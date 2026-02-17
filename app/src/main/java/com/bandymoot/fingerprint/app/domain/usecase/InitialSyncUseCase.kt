package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.repository.DeviceRepository
import com.bandymoot.fingerprint.app.domain.repository.ManagerRepository
import com.bandymoot.fingerprint.app.domain.repository.UserRepository
import com.bandymoot.fingerprint.app.utils.AppConstant
import javax.inject.Inject

class InitialSyncUseCase @Inject constructor(
    private val managerRepository: ManagerRepository,
    private val deviceRepository: DeviceRepository,
    private val userRepository: UserRepository
) {

    suspend fun execute(): RepositoryResult<Unit> {
        val managerSync = managerRepository.sync()
        if(managerSync is RepositoryResult.Failed) return managerSync

        val deviceSync = deviceRepository.sync()
        val userSync = userRepository.sync()

        AppConstant.debugMessage("CHECK DEVICE SYNC::$deviceSync")
        AppConstant.debugMessage("CHECK USER SYNC::$userSync")

        if(deviceSync is RepositoryResult.Failed) return deviceSync
        if(userSync is RepositoryResult.Failed) return userSync
        return RepositoryResult.Success(Unit)
    }
}