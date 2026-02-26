package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.domain.model.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.RepositoryResultAdvanced
import com.bandymoot.fingerprint.app.domain.repository.AttendanceRepository
import com.bandymoot.fingerprint.app.domain.repository.UserRepository
import com.bandymoot.fingerprint.app.utils.AppConstant
import java.time.LocalDate
import javax.inject.Inject

class SyncAttendanceUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val attendanceRepository: AttendanceRepository
) {
    suspend operator fun invoke(startLocalDate: LocalDate, endLocalDate: LocalDate): RepositoryResultAdvanced<Unit> {

        val startDate = startLocalDate.format(AppConstant.dateTimeFormatter)
            ?: return RepositoryResultAdvanced.Failed(throwable = Exception("Invalid Start Date"))
        val endDate = endLocalDate.format(AppConstant.dateTimeFormatter)
            ?: return RepositoryResultAdvanced.Failed(throwable = Exception("Invalid End Date"))

        // ---- ==== REMOTE SECTION ==== ----
        // Get Result from Api
        val apiResult = attendanceRepository.fetchFromApi(startDate = startDate, endDate = endDate)
        if(apiResult is RepositoryResult.Failed) {
            return RepositoryResultAdvanced.Failed(
                throwable = apiResult.throwable,
                descriptiveError = apiResult.descriptiveError
            )
        }

        // Extract the data from remote
        val remoteData = (apiResult as RepositoryResult.Success).data

        // ---- ==== REPOSITORY SECTION ==== ----
        // Get Valid IDs from User Repository
        val validIdResult = userRepository.getAllUserIds()
        if(validIdResult is RepositoryResult.Failed) {
            return RepositoryResultAdvanced.Failed(
                throwable = validIdResult.throwable,
                descriptiveError = validIdResult.descriptiveError
            )
        }

        // Extract the data from repo and convert it into Set (for faster check-up)
        val validUserIds = (validIdResult as RepositoryResult.Success).data.toSet()

        // ---- ==== FILTERING && STORING ==== ----
        // Filter -> Valid / In-Valid Attendance Data.
        val (valid, invalid) = remoteData.partition { it.userId in validUserIds }

        if(valid.isNotEmpty()) {
            val dbResponse = attendanceRepository.saveToDb(valid)
            // Error at DB sync level, must be at the top address! than inValid filtration.
            if(dbResponse is RepositoryResult.Failed) {
                return RepositoryResultAdvanced.Failed(
                    throwable = dbResponse.throwable,
                    descriptiveError = dbResponse.descriptiveError
                )
            }
        }

        if(invalid.isNotEmpty()) {
            return RepositoryResultAdvanced.PartialSuccess(
                data = Unit,
                errorMessage = "Saved ${valid.count()} records. ${invalid.count()} were skipped due to missing user profiles."
            )
        }
        return RepositoryResultAdvanced.Success(data = Unit)
    }
}