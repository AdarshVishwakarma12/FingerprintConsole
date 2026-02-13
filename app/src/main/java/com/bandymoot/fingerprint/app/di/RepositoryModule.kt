package com.bandymoot.fingerprint.app.di

import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.data.local.dao.AttendanceRecordDao
import com.bandymoot.fingerprint.app.data.local.dao.AuditLogDao
import com.bandymoot.fingerprint.app.data.local.dao.AuthenticationLogDao
import com.bandymoot.fingerprint.app.data.local.dao.DeviceDao
import com.bandymoot.fingerprint.app.data.local.dao.FingerprintDao
import com.bandymoot.fingerprint.app.data.local.dao.ManagerDao
import com.bandymoot.fingerprint.app.data.local.dao.OrganizationDao
import com.bandymoot.fingerprint.app.data.local.dao.UserDao
import com.bandymoot.fingerprint.app.data.remote.api.ApiServices
import com.bandymoot.fingerprint.app.data.repository.AttendanceRepositoryImpl
import com.bandymoot.fingerprint.app.data.repository.AuthRepositoryImpl
import com.bandymoot.fingerprint.app.data.repository.DeviceRepositoryImpl
import com.bandymoot.fingerprint.app.data.repository.EnrollmentRepositoryImpl
import com.bandymoot.fingerprint.app.data.repository.FakeDataRepository
import com.bandymoot.fingerprint.app.data.repository.ManagerRepositoryImpl
import com.bandymoot.fingerprint.app.data.repository.UserRepositoryImpl
import com.bandymoot.fingerprint.app.data.websocket.EnrollmentSocketDataSourceImpl
import com.bandymoot.fingerprint.app.domain.repository.AttendanceRepository
import com.bandymoot.fingerprint.app.domain.repository.AuthRepository
import com.bandymoot.fingerprint.app.domain.repository.DeviceRepository
import com.bandymoot.fingerprint.app.domain.repository.EnrollmentRepository
import com.bandymoot.fingerprint.app.domain.repository.ManagerRepository
import com.bandymoot.fingerprint.app.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideEnrollmentSocketDataSourceImpl(
        client: OkHttpClient,
        request: Request
    ): EnrollmentSocketDataSourceImpl {
        return EnrollmentSocketDataSourceImpl(
            client = client,
            request = request
        )
    }

    @Provides
    @Singleton
    fun provideEnrollmentRepositoryImpl(
        enrollmentSocketDataSourceImpl: EnrollmentSocketDataSourceImpl,
        apiServices: ApiServices,
        tokenProvider: TokenProvider
    ): EnrollmentRepository {
        return EnrollmentRepositoryImpl(
            enrollmentSocketDataSourceImpl = enrollmentSocketDataSourceImpl,
            apiServices = apiServices,
            tokenProvider = tokenProvider
        )
    }

    @Provides
    fun provideFakeDataRepository(
        organizationDao: OrganizationDao,
        managerDao: ManagerDao,
        deviceDao: DeviceDao,
        userDao: UserDao,
        fingerprintDao: FingerprintDao,
        authenticationLogDao: AuthenticationLogDao,
        auditLogDao: AuditLogDao,
        attendanceRecordDao: AttendanceRecordDao
    ): FakeDataRepository {
        return FakeDataRepository(
            organizationDao,
            managerDao,
            deviceDao,
            userDao,
            fingerprintDao,
            authenticationLogDao,
            auditLogDao,
            attendanceRecordDao
        )
    }

    @Provides
    @Singleton
    fun provideDeviceRepository(
        deviceDao: DeviceDao,
        apiServices: ApiServices,
        tokenProvider: TokenProvider,
        appDatabase: AppDatabase
    ): DeviceRepository {
        return DeviceRepositoryImpl(
            deviceDao = deviceDao,
            apiServices = apiServices,
            tokenProvider = tokenProvider,
            appDatabase = appDatabase
        )
    }

    @Provides
    @Singleton
    fun providesUserRepository(
        apiServices: ApiServices,
        userDao: UserDao,
        tokenProvider: TokenProvider,
        appDatabase: AppDatabase
    ): UserRepository {
        return UserRepositoryImpl(
            apiServices = apiServices,
            userDao = userDao,
            appDatabase = appDatabase,
            tokenProvider = tokenProvider
        )
    }

    @Provides
    @Singleton
    fun provideAttendanceRepository(
        apiService: ApiServices,
        attendanceRecordDao: AttendanceRecordDao,
        appDatabase: AppDatabase
    ): AttendanceRepository {
        return AttendanceRepositoryImpl(
            apiServices = apiService,
            attendanceRecordDao = attendanceRecordDao,
            appDatabase = appDatabase
        )
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiServices: ApiServices,
        tokenProvider: TokenProvider
    ): AuthRepository {
        return AuthRepositoryImpl(
            apiServices,
            tokenProvider
        )
    }

    @Provides
    @Singleton
    fun provideManagerRepository(
        apiServices: ApiServices,
        tokenProvider: TokenProvider,
        organizationDao: OrganizationDao,
        managerDao: ManagerDao,
        appDatabase: AppDatabase
    ): ManagerRepository {
        return ManagerRepositoryImpl(
            apiServices = apiServices,
            tokenProvider = tokenProvider,
            managerDao = managerDao,
            organizationDao = organizationDao,
            appDatabase = appDatabase
        )
    }
}