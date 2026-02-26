package com.bandymoot.fingerprint.app.domain.repository

import com.bandymoot.fingerprint.app.data.dto.AttendanceDto
import com.bandymoot.fingerprint.app.domain.model.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.AttendanceRecord
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.YearMonth

interface AttendanceRepository {
    fun observeAll(): Flow<List<AttendanceRecord>>
    suspend fun getAttendanceByDate(date: LocalDate): RepositoryResult<List<AttendanceRecord>>
    suspend fun getAttendanceByDateAndDevice(date: Long, deviceId: String): RepositoryResult<List<AttendanceRecord>>
    suspend fun getAttendanceByMonthAndUser(startOfMonth: Long, endOfMonth: Long, userId: String): RepositoryResult<List<AttendanceRecord>>
    suspend fun fetchFromApi(startDate: String, endDate: String): RepositoryResult<List<AttendanceDto>>
    suspend fun saveToDb(data: List<AttendanceDto>): RepositoryResult<Unit>
}