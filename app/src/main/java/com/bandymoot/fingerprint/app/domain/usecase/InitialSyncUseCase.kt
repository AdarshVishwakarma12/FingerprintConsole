package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.domain.model.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.RepositoryResultAdvanced
import com.bandymoot.fingerprint.app.domain.repository.AttendanceRepository
import com.bandymoot.fingerprint.app.domain.repository.DeviceRepository
import com.bandymoot.fingerprint.app.domain.repository.ManagerRepository
import com.bandymoot.fingerprint.app.domain.repository.UserRepository
import com.bandymoot.fingerprint.app.utils.AppConstant
import java.time.LocalDate
import javax.inject.Inject

class InitialSyncUseCase @Inject constructor(
    private val managerRepository: ManagerRepository,
    private val deviceRepository: DeviceRepository,
    private val userRepository: UserRepository,
    private val syncAttendanceUseCase: SyncAttendanceUseCase
) {
    suspend fun execute(): RepositoryResult<Unit> {
        val managerSync = managerRepository.sync()
        if(managerSync is RepositoryResult.Failed) return managerSync

        val deviceSync = deviceRepository.sync()
        val userSync = userRepository.sync()
        val attendanceSync = syncAttendanceUseCase(
            startLocalDate = LocalDate.now(),
            endLocalDate = LocalDate.now()
        )

        AppConstant.debugMessage("CHECK DEVICE SYNC::$deviceSync")
        AppConstant.debugMessage("CHECK USER SYNC::$userSync")
        AppConstant.debugMessage("CHECK ATTENDANCE SYNC;;$attendanceSync")

        if(deviceSync is RepositoryResult.Failed) return deviceSync
        if(userSync is RepositoryResult.Failed) return userSync
        // Yup the Mapper is important, but we'll do that in next version!
        if(attendanceSync is RepositoryResultAdvanced.Failed) return RepositoryResult.Failed(throwable = attendanceSync.throwable, descriptiveError = attendanceSync.descriptiveError)

        return RepositoryResult.Success(Unit)
    }
}