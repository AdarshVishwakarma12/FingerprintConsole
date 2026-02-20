package com.bandymoot.fingerprint.app.domain.repository

import com.bandymoot.fingerprint.app.data.dto.EnrollNewDeviceRequest
import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.Device
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun observeAll(): Flow<List<Device>>
    fun observeDeviceByCurrentManager(): Flow<RepositoryResult<List<Device>>>
    suspend fun delete(): RepositoryResult<Unit>
    suspend fun enrollNewDevice(requestData: EnrollNewDeviceRequest): RepositoryResult<Unit>
    suspend fun sync(): RepositoryResult<Unit>
}