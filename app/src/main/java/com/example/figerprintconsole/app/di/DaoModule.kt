package com.example.figerprintconsole.app.di

import com.example.figerprintconsole.app.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideOrganizationDao(db: AppDatabase): OrganizationDao =
        db.organizationDao()

    @Provides
    fun provideManagerDao(db: AppDatabase): ManagerDao =
        db.managerDao()

    @Provides
    fun provideDeviceDao(db: AppDatabase): DeviceDao =
        db.deviceDao()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao =
        db.userDao()

    @Provides
    fun provideFingerprintDao(db: AppDatabase): FingerprintDao =
        db.fingerprintDao()

    @Provides
    fun provideAuthenticationLogDao(db: AppDatabase): AuthenticationLogDao =
        db.authenticationLogDao()

    @Provides
    fun provideAuditLogDao(db: AppDatabase): AuditLogDao =
        db.auditLogDao()

    @Provides
    fun provideAttendanceRecordDao(db: AppDatabase): AttendanceRecordDao =
        db.attendanceRecordDao()
}
