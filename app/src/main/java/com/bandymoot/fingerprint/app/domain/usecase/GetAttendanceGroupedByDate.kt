package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.AttendanceRecord
import com.bandymoot.fingerprint.app.domain.repository.AttendanceRepository
import com.bandymoot.fingerprint.app.utils.AppConstant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject

class GetAttendanceGroupedByDate @Inject constructor(
    private val attendanceRepositoryImpl: AttendanceRepository
) {
    suspend operator fun invoke(userServerId: String, currentYearMonth: YearMonth): RepositoryResult<Map<String, List<AttendanceRecord>>> {

        // Calculate Start and End Time
        val constZoneId = ZoneId.of(AppConstant.ZONE_ID)

        val startOfMonth = LocalDate
            .of(currentYearMonth.year, currentYearMonth.month, 1)
            .atStartOfDay(constZoneId)
            .toInstant()
            .toEpochMilli()

        val endOfMonth = LocalDate
            .of(currentYearMonth.year, currentYearMonth.month, currentYearMonth.lengthOfMonth())
            .atStartOfDay(constZoneId)
            .toInstant()
            .toEpochMilli()

        // Call Repository
        val result = attendanceRepositoryImpl.getAttendanceByMonthAndUser(startOfMonth = startOfMonth, endOfMonth = endOfMonth, userId = userServerId)

        return when(result) {
            is RepositoryResult.Success -> {

                // Build the Efficient Map
                val daysInMonth = currentYearMonth.lengthOfMonth()
                val dates = (1..daysInMonth).map { day ->
                    LocalDate.of(currentYearMonth.year, currentYearMonth.month, day)
                }

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