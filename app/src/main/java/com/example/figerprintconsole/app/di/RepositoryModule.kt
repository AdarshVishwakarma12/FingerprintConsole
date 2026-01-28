package com.example.figerprintconsole.app.di

import com.example.figerprintconsole.app.data.local.dao.AuditLogDao
import com.example.figerprintconsole.app.data.local.dao.AuthenticationLogDao
import com.example.figerprintconsole.app.data.local.dao.DeviceDao
import com.example.figerprintconsole.app.data.local.dao.FingerprintDao
import com.example.figerprintconsole.app.data.local.dao.ManagerDao
import com.example.figerprintconsole.app.data.local.dao.OrganizationDao
import com.example.figerprintconsole.app.data.local.dao.UserDao
import com.example.figerprintconsole.app.data.remote.api.ApiServices
import com.example.figerprintconsole.app.data.repository.DeviceRepositoryImpl
import com.example.figerprintconsole.app.data.repository.EnrollmentRepositoryImpl
import com.example.figerprintconsole.app.data.repository.FakeDataRepository
import com.example.figerprintconsole.app.data.repository.UserRepositoryImpl
import com.example.figerprintconsole.app.data.websocket.EnrollmentSocketDataSourceImpl
import com.example.figerprintconsole.app.domain.repository.DeviceRepository
import com.example.figerprintconsole.app.domain.repository.UserRepository
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
        enrollmentSocketDataSourceImpl: EnrollmentSocketDataSourceImpl
    ): EnrollmentRepositoryImpl {
        return EnrollmentRepositoryImpl(enrollmentSocketDataSourceImpl = enrollmentSocketDataSourceImpl)
    }

    @Provides
    fun provideFakeDataRepository(
        organizationDao: OrganizationDao,
        managerDao: ManagerDao,
        deviceDao: DeviceDao,
        userDao: UserDao,
        fingerprintDao: FingerprintDao,
        authenticationLogDao: AuthenticationLogDao,
        auditLogDao: AuditLogDao
    ): FakeDataRepository {
        return FakeDataRepository(
            organizationDao,
            managerDao,
            deviceDao,
            userDao,
            fingerprintDao,
            authenticationLogDao,
            auditLogDao
        )
    }

    @Provides
    @Singleton
    fun provideDeviceRepository(
        deviceDao: DeviceDao
    ): DeviceRepository {
        return DeviceRepositoryImpl(deviceDao = deviceDao)
    }

    @Provides
    @Singleton
    fun providesUserRepository(
        apiServices: ApiServices,
        userDao: UserDao,
        appDatabase: AppDatabase
    ): UserRepository {
        return UserRepositoryImpl(
            apiServices = apiServices,
            userDao = userDao,
            appDatabase = appDatabase
        )
    }
}