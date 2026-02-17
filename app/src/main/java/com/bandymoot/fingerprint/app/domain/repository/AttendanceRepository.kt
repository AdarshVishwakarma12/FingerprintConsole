package com.bandymoot.fingerprint.app.domain.repository

import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.AttendanceRecord
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.YearMonth

interface AttendanceRepository {
    fun observeAll(): Flow<List<AttendanceRecord>>
    suspend fun getAttendanceByDate(date: LocalDate): RepositoryResult<List<AttendanceRecord>>
    suspend fun getAttendanceByDateAndDevice(date: Long, deviceId: String): RepositoryResult<List<AttendanceRecord>>
    suspend fun getAttendanceByMonthAndUser(startOfMonth: Long, endOfMonth: Long, userId: String): RepositoryResult<List<AttendanceRecord>>
    suspend fun sync(startDate: String, endDate: String): RepositoryResult<Unit>
}