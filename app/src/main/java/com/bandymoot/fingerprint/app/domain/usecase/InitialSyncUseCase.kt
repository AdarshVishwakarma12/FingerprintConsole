package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
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
    private val attendanceRepository: AttendanceRepository
) {

    suspend fun execute(): RepositoryResult<Unit> {
        val managerSync = managerRepository.sync()
        if(managerSync is RepositoryResult.Failed) return managerSync

        val deviceSync = deviceRepository.sync()
        val userSync = userRepository.sync()
        val attendanceSync = attendanceRepository.sync(
            startDate = LocalDate.now().format(AppConstant.dateTimeFormatter),
            endDate = LocalDate.now().format(AppConstant.dateTimeFormatter)
        )

        AppConstant.debugMessage("CHECK DEVICE SYNC::$deviceSync")
        AppConstant.debugMessage("CHECK USER SYNC::$userSync")
        AppConstant.debugMessage("CHECK ATTENDANCE SYNC;;$attendanceSync")

        if(deviceSync is RepositoryResult.Failed) return deviceSync
        if(userSync is RepositoryResult.Failed) return userSync
        if(attendanceSync is RepositoryResult.Failed) return attendanceSync

        return RepositoryResult.Success(Unit)
    }
}