package com.bandymoot.fingerprint.app.data.repository

import androidx.room.withTransaction
import com.bandymoot.fingerprint.app.data.local.dao.AttendanceRecordDao
import com.bandymoot.fingerprint.app.data.mapper.toDomain
import com.bandymoot.fingerprint.app.data.mapper.toEntity
import com.bandymoot.fingerprint.app.data.remote.api.ApiServices
import com.bandymoot.fingerprint.app.data.remote.safeApiCall
import com.bandymoot.fingerprint.app.di.AppDatabase
import com.bandymoot.fingerprint.app.domain.model.AttendanceRecord
import com.bandymoot.fingerprint.app.domain.repository.AttendanceRepository
import com.bandymoot.fingerprint.app.utils.AppConstant
import com.bandymoot.fingerprint.app.utils.DebugType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
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
                .atStartOfDay(ZoneId.of(AppConstant.ZONE_ID))
                .toInstant()
                .toEpochMilli()
            val endOfDay = startOfDay
                .plus(1) - 1

            val response = attendanceRecordDao.extractAttendanceByDate(startOfDay, endOfDay)

            AppConstant.debugMessage("StartDay$startOfDay EndDay: $endOfDay", debugType = DebugType.INFO)

            return RepositoryResult.Success(response.map { it.toDomain() })
        } catch (e: Exception) {
            return RepositoryResult.Failed(e)
        }
    }

    override suspend fun getAttendanceByDateAndDevice(
        date: Long,     // FUTURE::Change this to LocalDate && do convert into long data type over here! Reduce Confusion right now!
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
        yearMonth: YearMonth, // MonthYear
        userId: String
    ): RepositoryResult<List<AttendanceRecord>> {
        try {
            // Calc Start of Month [ it will be consistent over change in TimeZone! ]
            val startOfMonth = LocalDate
                .of(yearMonth.year, yearMonth.month, 1)
                .atStartOfDay(ZoneId.of(AppConstant.ZONE_ID))
                .toInstant()
                .toEpochMilli()

            val endOfMonth = LocalDate
                .of(yearMonth.year, yearMonth.month, yearMonth.lengthOfMonth())
                .atStartOfDay(ZoneId.of(AppConstant.ZONE_ID))
                .toInstant()
                .toEpochMilli()

            AppConstant.debugMessage("startOfMonth: $startOfMonth && endOfMonth: $endOfMonth")
            val response = attendanceRecordDao.extractAttendanceByMonthAndUser(userId, startOfMonth, endOfMonth)
            return RepositoryResult.Success(response.map { it.toDomain() })
        } catch (e: Exception) {
            return RepositoryResult.Failed(e)
        }
    }

    override suspend fun sync(startDate: String, endDate: String): RepositoryResult<Unit> {
        return try {

            val response = safeApiCall { apiServices.getAttendanceDataByDate(startDate, endDate) }

            if(response is RepositoryResult.Failed) return response

            val attendanceRecordList = (response as RepositoryResult.Success).data.data

            appDatabase.withTransaction {
                attendanceRecordList.map { attendanceRecordDao.upsert(it.toEntity()) }
            }

            RepositoryResult.Success(Unit)
        } catch (e: kotlin.Exception) {
            RepositoryResult.Failed(e)
        }
    }
}