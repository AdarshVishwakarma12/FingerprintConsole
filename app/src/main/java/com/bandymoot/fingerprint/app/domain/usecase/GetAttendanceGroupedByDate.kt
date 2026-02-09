package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.AttendanceRecord
import com.bandymoot.fingerprint.app.domain.repository.AttendanceRepository
import com.bandymoot.fingerprint.app.utils.AppConstant
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class GetAttendanceGroupedByDate @Inject constructor(
    private val attendanceRepositoryImpl: AttendanceRepository
) {
    suspend operator fun invoke(userServerId: String, currentYearMonth: YearMonth): RepositoryResult<Map<String, List<AttendanceRecord>>> {

        val daysInMonth = currentYearMonth.lengthOfMonth()
        val dates = (1..daysInMonth).map { day ->
            LocalDate.of(currentYearMonth.year, currentYearMonth.month, day)
        }

        AppConstant.debugMessage("Show Option::::UserID $userServerId")
        val result = attendanceRepositoryImpl.getAttendanceByMonthAndUser(userId = userServerId, yearMonth = currentYearMonth)

        return when(result) {
            is RepositoryResult.Success -> {
                AppConstant.debugMessage("Show Option:::: ${result.data}")
                val map = dates.associate { day ->
                    val key = day.toString()
                    key to result.data.filter { it.dateString == key }
                }
                RepositoryResult.Success(map) // Must take multiple values!
            }
            is RepositoryResult.Failed -> {
                result
            }
        }
    }
}