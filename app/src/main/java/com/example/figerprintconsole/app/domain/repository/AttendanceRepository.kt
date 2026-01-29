package com.example.figerprintconsole.app.domain.repository

import com.example.figerprintconsole.app.data.repository.RepositoryResult
import com.example.figerprintconsole.app.domain.model.AttendanceRecord
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AttendanceRepository {
    fun observeAll(): Flow<List<AttendanceRecord>>
    suspend fun getAttendanceByDate(date: LocalDate): RepositoryResult<List<AttendanceRecord>>
    suspend fun getAttendanceByDateAndDevice(date: Long, deviceId: String): RepositoryResult<List<AttendanceRecord>>
    suspend fun getAttendanceByMonthAndUser(month: Int, userId: String): RepositoryResult<List<AttendanceRecord>>
    suspend fun sync(): RepositoryResult<Nothing>
}