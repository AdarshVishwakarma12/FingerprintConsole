package com.bandymoot.fingerprint.app.data.repository

import androidx.room.withTransaction
import com.bandymoot.fingerprint.app.data.dto.EnrollNewDeviceRequest
import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.data.local.dao.DeviceDao
import com.bandymoot.fingerprint.app.data.local.entity.DeviceStatusEntityEnum
import com.bandymoot.fingerprint.app.data.mapper.toDomain
import com.bandymoot.fingerprint.app.data.mapper.toEntity
import com.bandymoot.fingerprint.app.data.remote.api.ApiServices
import com.bandymoot.fingerprint.app.data.remote.safeApiCall
import com.bandymoot.fingerprint.app.data.socket.SocketEvent
import com.bandymoot.fingerprint.app.data.socket.SocketManager
import com.bandymoot.fingerprint.app.data.socket.SocketTopicDeviceStatus
import com.bandymoot.fingerprint.app.di.AppDatabase
import com.bandymoot.fingerprint.app.domain.model.Device
import com.bandymoot.fingerprint.app.domain.repository.DeviceRepository
import com.bandymoot.fingerprint.app.network.ErrorResolver
import com.bandymoot.fingerprint.app.utils.AppConstant
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    val deviceDao: DeviceDao,
    val apiServices: ApiServices,
    val tokenProvider: TokenProvider,
    val appDatabase: AppDatabase,
    @ApplicationContext private val externalScope: CoroutineScope
): DeviceRepository {

    init {
        externalScope.launch {
            SocketManager.socketEvent.collect { socketEvent ->
                when(socketEvent) {
                    is SocketEvent.Device -> {
                        if(socketEvent.data.status == SocketTopicDeviceStatus.ONLINE) {
                            AppConstant.debugMessage("DEVICE GOES TO ONLINE: ${socketEvent.data}", tag = "NETWORK")
                            deviceDao.updateDeviceStatusByCode(socketEvent.data.deviceId, DeviceStatusEntityEnum.ACTIVE)
                        } else {
                            AppConstant.debugMessage("DEVICE GOES TO OFFLINE: ${socketEvent.data}", tag = "NETWORK")
                            deviceDao.updateDeviceStatusByCode(socketEvent.data.deviceId, DeviceStatusEntityEnum.OFFLINE)
                        }
                    }
                    else -> { }
                }
            }
        }
    }

    override fun observeAll(): Flow<List<Device>> = flow {
        deviceDao.getAll().collect { devices ->
            AppConstant.debugMessage("Entity Updated: check: $devices", tag = "NETWORK")
            emit(
                devices.map { device ->
                    device.toDomain()
                }
            )
        }
    }.catch { e ->
    }

    override fun observeDeviceByCurrentManager(): Flow<RepositoryResult<List<Device>>> = flow {
        // Define a permanent manager Id
        // managerId is coming from SharedPreference! -> auth page saves it.
        val managerId = tokenProvider.getUserId() ?: return@flow emit(RepositoryResult.Failed(Exception("Manager Not Found")))

        val dbFLow = deviceDao.getDevicesByManagerId(managerId)
            .map { entities ->
                RepositoryResult.Success(entities.map { it.toDomain() })
            }
            .catch { e ->
                emit(RepositoryResult.Failed(Exception(e.message ?: e.localizedMessage)))
            }

        emitAll(dbFLow)
    }.flowOn(Dispatchers.Default) // you are make it harder to test! Yes I am. Maybe fix later.

    override suspend fun delete(): RepositoryResult<Unit> {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }

    override suspend fun enrollNewDevice(requestData: EnrollNewDeviceRequest): RepositoryResult<Unit> {

        val tokenValue = tokenProvider.tokenFLow.value ?: return RepositoryResult.Failed(Exception("Token Not Found"))
        val response = safeApiCall { apiServices.enrollNewDevice(tokenValue, requestData) }

        if(response is RepositoryResult.Failed) RepositoryResult.Failed(throwable = response.throwable, descriptiveError = ErrorResolver.resolveHttpError(response.hashCode()))
        if((response as RepositoryResult.Success).data.success) return RepositoryResult.Success(Unit)

        return RepositoryResult.Failed(Exception("Couldn't Enroll Device"))
    }

    override suspend fun sync(): RepositoryResult<Unit> {
        val token = tokenProvider.tokenFLow.value ?: return RepositoryResult.Failed(Exception("Token Not Found"))
        return try {

            val response = safeApiCall { apiServices.getAllDevices(token) }
            if(response is RepositoryResult.Failed) return response

            val listDeviceDto = (response as RepositoryResult.Success).data.data
            val safeListDeviceEntity = listDeviceDto.mapNotNull { it.toEntity() }

            appDatabase.withTransaction {
                safeListDeviceEntity.map { deviceDao.upsert(it) }
            }

            RepositoryResult.Success(Unit)

        } catch (e: Exception) {
            RepositoryResult.Failed(throwable = e, descriptiveError = ErrorResolver.resolve(e))
        }
    }
}