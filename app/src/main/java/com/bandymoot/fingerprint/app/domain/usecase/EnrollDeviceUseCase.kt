package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.data.dto.EnrollNewDeviceRequest
import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.repository.DeviceRepository
import java.lang.Exception
import javax.inject.Inject

class EnrollDeviceUseCase @Inject constructor(
    private val deviceRepository: DeviceRepository
) {
    suspend operator fun invoke(
        deviceName: String,
        deviceCode: String,
        deviceSecret: String
    ): RepositoryResult<Unit> {
        // There is no separation of Error module of Repository and UiLayer! This is intensional, cause refactoring take a lot of time, and expensive right now!.
        // Better to be ship now, and debug later.

        // Validate data
        if(deviceName.isEmpty()) return RepositoryResult.Failed(Exception("Empty Device Name"))
        if(deviceCode.isEmpty()) return RepositoryResult.Failed(Exception("Empty Device Code"))
        if(deviceSecret.isEmpty()) return RepositoryResult.Failed(Exception("Empty Device Secret"))

        val requestData = EnrollNewDeviceRequest(
            deviceName =  deviceName,
            deviceCode = deviceCode,
            secretKey = deviceSecret
        )

        return deviceRepository.enrollNewDevice(requestData = requestData)
    }
}