package com.example.figerprintconsole.app.data.repository

import com.example.figerprintconsole.app.data.local.dao.AttendanceRecordDao
import com.example.figerprintconsole.app.data.mapper.toDomain
import com.example.figerprintconsole.app.data.remote.api.ApiServices
import com.example.figerprintconsole.app.di.AppDatabase
import com.example.figerprintconsole.app.domain.model.AttendanceRecord
import com.example.figerprintconsole.app.domain.repository.AttendanceRepository
import com.example.figerprintconsole.app.utils.AppConstant
import com.example.figerprintconsole.app.utils.DebugType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    val apiServices: ApiServices,
    val attendanceRecordDao: AttendanceRecordDao,
    val appDatabase: AppDatabase
): AttendanceRepository {
    override fun observeAll(): Flow<List<AttendanceRecord>> = flow {
        attendanceRecordDao.observeAll().collect { attendanceData ->
            emit(
                attendanceData.map { it.toDomain() }
            )
        }
    }.catch {
        Throwable("Error Collecting Data")
    }

    override suspend fun getAttendanceByDate(date: LocalDate): RepositoryResult<List<AttendanceRecord>> {
        try {
            val startOfDay = LocalDate
                .of(date.year, date.month, date.dayOfMonth)
                .atStartOfDay(ZoneId.of("Asia/Kolkata"))
                .toInstant()
                .toEpochMilli()
            val endOfDay = startOfDay
                .plus(1) - 1

            val response = attendanceRecordDao.extractAttendanceByDate(startOfDay, endOfDay)

            AppConstant.debugMessage("StartDay" + startOfDay + " EndDay: " + endOfDay, debugType = DebugType.INFO)

            return RepositoryResult.Success(response.map { it.toDomain() })
        } catch (e: Exception) {
            return RepositoryResult.Failed(e)
        }
    }

    override suspend fun getAttendanceByDateAndDevice(
        date: Long,
        deviceId: String
    ): RepositoryResult<List<AttendanceRecord>> {
        try {
            val response = attendanceRecordDao.extractAttendanceByDateAndDevice(date, deviceId)
            return RepositoryResult.Success(response.map { it.toDomain() })
        } catch (e: Exception) {
            return RepositoryResult.Failed(e)
        }
    }

    override suspend fun getAttendanceByMonthAndUser(
        month: Int,
        userId: String
    ): RepositoryResult<List<AttendanceRecord>> {
        try {
            val response = attendanceRecordDao.extractAttendanceByMonthAndUser(month, userId)
            return RepositoryResult.Success(response.map { it.toDomain() })
        } catch (e: Exception) {
            return RepositoryResult.Failed(e)
        }
    }

    override suspend fun sync(): RepositoryResult<Nothing> {
        return RepositoryResult.Failed(Throwable("Not Implemented"))
    }
}