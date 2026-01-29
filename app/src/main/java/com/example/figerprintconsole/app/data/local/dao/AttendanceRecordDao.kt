package com.example.figerprintconsole.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.figerprintconsole.app.data.local.entity.AttendanceRecordEntity
import com.example.figerprintconsole.app.data.local.projection.AttendanceRecordEntityProjector
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceRecordDao {

    @Query("SELECT * FROM attendance_records")
    fun observeAll(): Flow<List<AttendanceRecordEntityProjector>>

    @Query("SELECT * FROM attendance_records WHERE date BETWEEN :startOfDay AND :endOfDay")
    suspend fun extractAttendanceByDate(startOfDay: Long, endOfDay: Long): List<AttendanceRecordEntityProjector>

    @Query("SELECT * FROM attendance_records WHERE date=:date AND device_server_id=:deviceServerId")
    suspend fun extractAttendanceByDateAndDevice(date: Long, deviceServerId: String): List<AttendanceRecordEntityProjector>

    @Query("SELECT * FROM attendance_records WHERE month=:month AND user_server_id=:userServerId")
    suspend fun extractAttendanceByMonthAndUser(month: Int, userServerId: String): List<AttendanceRecordEntityProjector>

    @Upsert
    suspend fun upsert(entity: AttendanceRecordEntity)
}