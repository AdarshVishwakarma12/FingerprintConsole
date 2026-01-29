package com.example.figerprintconsole.app.di

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.figerprintconsole.app.data.local.dao.*
import com.example.figerprintconsole.app.data.local.entity.*
import com.example.figerprintconsole.app.utils.EnumConverters

@Database(
    entities = [
        OrganizationEntity::class,
        ManagerEntity::class,
        DeviceEntity::class,
        UserEntity::class,
        FingerprintEntity::class,
        AuthenticationLogEntity::class,
        AuditLogEntity::class,
        AttendanceRecordEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(EnumConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun organizationDao(): OrganizationDao
    abstract fun managerDao(): ManagerDao
    abstract fun deviceDao(): DeviceDao
    abstract fun userDao(): UserDao
    abstract fun fingerprintDao(): FingerprintDao
    abstract fun authenticationLogDao(): AuthenticationLogDao
    abstract fun auditLogDao(): AuditLogDao
    abstract fun attendanceRecordDao(): AttendanceRecordDao
}
