package com.example.figerprintconsole.app.data.repository

import com.example.figerprintconsole.app.data.local.dao.DeviceDao
import com.example.figerprintconsole.app.data.mapper.toDomain
import com.example.figerprintconsole.app.domain.model.Device
import com.example.figerprintconsole.app.domain.repository.DeviceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    val deviceDao: DeviceDao
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

    override suspend fun delete(): RepositoryResult {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }

    override suspend fun sync(): RepositoryResult {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }
}