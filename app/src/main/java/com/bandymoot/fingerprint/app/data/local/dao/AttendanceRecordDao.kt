package com.bandymoot.fingerprint.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.bandymoot.fingerprint.app.data.local.entity.AttendanceRecordEntity
import com.bandymoot.fingerprint.app.data.local.projection.AttendanceRecordEntityProjector
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceRecordDao {

    @Query("SELECT * FROM attendance_records")
    fun observeAll(): Flow<List<AttendanceRecordEntityProjector>>

    @Query("SELECT * FROM attendance_records WHERE date BETWEEN :startOfDay AND :endOfDay")
    suspend fun extractAttendanceByDate(startOfDay: Long, endOfDay: Long): List<AttendanceRecordEntityProjector>

    @Query("SELECT * FROM attendance_records WHERE date=:date AND device_server_id=:deviceServerId")
    suspend fun extractAttendanceByDateAndDevice(date: Long, deviceServerId: String): List<AttendanceRecordEntityProjector>

    @Query("SELECT * FROM attendance_records WHERE (user_server_id=:userServerId) AND (date BETWEEN :startOfMonth AND :endOfMonth)")
    suspend fun extractAttendanceByMonthAndUser(userServerId: String, startOfMonth: Long, endOfMonth: Long): List<AttendanceRecordEntityProjector>

    @Upsert
    suspend fun upsert(entity: AttendanceRecordEntity)
}
// val startOfDay = LocalDate
//                .of(date.year, date.month, date.dayOfMonth)
//                .atStartOfDay(ZoneId.of(AppConstant.ZONE_ID))
//                .toInstant()
//                .toEpochMilli()