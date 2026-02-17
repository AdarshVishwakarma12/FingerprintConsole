package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.AttendanceRecord
import com.bandymoot.fingerprint.app.domain.repository.AttendanceRepository
import com.bandymoot.fingerprint.app.utils.AppConstant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import javax.inject.Inject

class GetAttendanceByUserAndMonthUseCase @Inject constructor(
    private val attendanceRepository: AttendanceRepository
) {
    suspend operator fun invoke(
        yearMonth: YearMonth, // MonthYear
        userId: String
    ): RepositoryResult<List<AttendanceRecord>> {

        val constZoneId = ZoneId.of(AppConstant.ZONE_ID)

        val startOfMonth = LocalDate
            .of(yearMonth.year, yearMonth.month, 1)
            .atStartOfDay(constZoneId)
            .toInstant()
            .toEpochMilli()

        val endOfMonth = LocalDate
            .of(yearMonth.year, yearMonth.month, yearMonth.lengthOfMonth())
            .atStartOfDay(constZoneId)
            .toInstant()
            .toEpochMilli()

        return attendanceRepository.getAttendanceByMonthAndUser(startOfMonth, endOfMonth, userId)
    }
}