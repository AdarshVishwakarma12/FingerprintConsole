package com.bandymoot.fingerprint.app.data.repository

import androidx.room.withTransaction
import com.bandymoot.fingerprint.app.data.dto.AttendanceDto
import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.data.local.dao.AttendanceRecordDao
import com.bandymoot.fingerprint.app.data.mapper.toDomain
import com.bandymoot.fingerprint.app.data.mapper.toEntity
import com.bandymoot.fingerprint.app.data.remote.api.ApiServices
import com.bandymoot.fingerprint.app.data.remote.safeApiCall
import com.bandymoot.fingerprint.app.di.AppDatabase
import com.bandymoot.fingerprint.app.domain.model.AttendanceRecord
import com.bandymoot.fingerprint.app.domain.model.RepositoryResult
import com.bandymoot.fingerprint.app.domain.repository.AttendanceRepository
import com.bandymoot.fingerprint.app.network.ErrorResolver
import com.bandymoot.fingerprint.app.utils.AppConstant
import com.bandymoot.fingerprint.app.utils.DebugType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices,
    private val attendanceRecordDao: AttendanceRecordDao,
    private val tokenProvider: TokenProvider,
    private val appDatabase: AppDatabase
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
        startOfMonth: Long,
        endOfMonth: Long,
        userId: String
    ): RepositoryResult<List<AttendanceRecord>> {
        try {
            // Calc Start of Month [ it will be consistent over change in TimeZone! ]
            val response = attendanceRecordDao.extractAttendanceByMonthAndUser(userId, startOfMonth, endOfMonth)
            return RepositoryResult.Success(response.map { it.toDomain() })
        } catch (e: Exception) {
            return RepositoryResult.Failed(e)
        }
    }

    override suspend fun fetchFromApi(startDate: String, endDate: String): RepositoryResult<List<AttendanceDto>> {
        val tokenValue = tokenProvider.tokenFLow.value ?: return RepositoryResult.Failed(Exception("Token Not Found"))

        try {
            val response = safeApiCall { apiServices.getAttendanceDataByDate(token = tokenValue, startDate = startDate, endDate = endDate) }
            val attendanceRecordList = (response as RepositoryResult.Success).data.data
            return RepositoryResult.Success(data = attendanceRecordList)
        } catch (e: Exception) {
            return RepositoryResult.Failed(throwable = e, descriptiveError = ErrorResolver.resolve(e))
        }
    }

    override suspend fun saveToDb(data: List<AttendanceDto>): RepositoryResult<Unit> {
        return try {
            appDatabase.withTransaction {
                data.forEach { attendanceRecordDao.upsert(it.toEntity()) }
            }
            RepositoryResult.Success(data = Unit)
        } catch (e: Exception) {
            RepositoryResult.Failed(throwable = e)
        }
    }

//    Split the function in two parts -> getResultFromApi + syncToLocalDB (far more testability, and loose coupling over the repositories)
//    override suspend fun sync(startDate: String, endDate: String): RepositoryResult<Unit> {
//
//        val tokenValue = tokenProvider.tokenFLow.value ?: return RepositoryResult.Failed(Exception("Token Not Found"))
//
//        return try {
//
//            val response = safeApiCall { apiServices.getAttendanceDataByDate(token = tokenValue, startDate = startDate, endDate = endDate) }
//
//            if(response is RepositoryResult.Failed) return response
//
//            val attendanceRecordList = (response as RepositoryResult.Success).data.data
//            // Filter AttendanceDto - Invalid UserId [ Check Validation of Foreign Key before 'withTransaction' ]
//            val validAttendanceRecordList = attendanceRecordList.map {  }
//
//            AppConstant.debugMessage("SUCCESS ATTENDANCE RECORD: $attendanceRecordList")
//            attendanceRecordList.map { AppConstant.debugMessage("INDIVIDUAL::$it") }
//            appDatabase.withTransaction {
//                attendanceRecordList.map { attendanceRecordDao.upsert(it.toEntity()) }
//            }
//
//            RepositoryResult.Success(Unit)
//        } catch (e: kotlin.Exception) {
//            AppConstant.debugMessage("ERROR TO SYNC THE ATTENDANCE DATA: ${e.message} ${e.localizedMessage}}")
//            RepositoryResult.Failed(e)
//        }
//    }
}