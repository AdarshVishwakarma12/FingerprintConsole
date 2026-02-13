package com.bandymoot.fingerprint.app.data.repository

import androidx.room.withTransaction
import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.data.local.dao.DeviceDao
import com.bandymoot.fingerprint.app.data.mapper.toDomain
import com.bandymoot.fingerprint.app.data.mapper.toEntity
import com.bandymoot.fingerprint.app.data.remote.api.ApiServices
import com.bandymoot.fingerprint.app.data.remote.safeApiCall
import com.bandymoot.fingerprint.app.di.AppDatabase
import com.bandymoot.fingerprint.app.domain.model.Device
import com.bandymoot.fingerprint.app.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    val deviceDao: DeviceDao,
    val apiServices: ApiServices,
    val tokenProvider: TokenProvider,
    val appDatabase: AppDatabase
): DeviceRepository {

    override fun observeAll(): Flow<List<Device>> = flow {
        deviceDao.getAll().collect { devices ->
            emit(
                devices.map { device ->
                    device.toDomain()
                }
            )
        }
    }.catch { e ->
    }

    override suspend fun observeDeviceByCurrentManager(): RepositoryResult<List<Device>> {
        try {
            // Define a permanent manager Id
            // as the auth page hasn't build and we don't have current manager info!!
            val managerId = "mgr-reg-1" // this should be stored under the sharedPref!
            val response = deviceDao.getDevicesByManagerId(managerId)
            return RepositoryResult.Success(response.map { it.toDomain() })
        } catch (e: Exception) {
            return RepositoryResult.Failed(e)
        }
    }

    override suspend fun delete(): RepositoryResult<Unit> {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }

    override suspend fun sync(): RepositoryResult<Unit> {
        val token = tokenProvider.tokenFLow.value ?: throw Exception("Token Not Found")
        return try {

            val response = safeApiCall { apiServices.getAllDevices(token) }
            if(response is RepositoryResult.Failed) return response

            val deviceEntity = (response as RepositoryResult.Success).data.data

            appDatabase.withTransaction {
                deviceEntity.map { deviceDao.upsert(it.toEntity()) }
            }

            RepositoryResult.Success(Unit)

        } catch (e: Exception) {
            RepositoryResult.Failed(throwable = e)
        }
    }
}