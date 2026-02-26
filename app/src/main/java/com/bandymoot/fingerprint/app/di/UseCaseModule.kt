package com.bandymoot.fingerprint.app.di

import com.bandymoot.fingerprint.app.data.repository.UserRepositoryImpl
import com.bandymoot.fingerprint.app.domain.repository.AttendanceRepository
import com.bandymoot.fingerprint.app.domain.repository.DeviceRepository
import com.bandymoot.fingerprint.app.domain.repository.EnrollmentRepository
import com.bandymoot.fingerprint.app.domain.repository.ManagerRepository
import com.bandymoot.fingerprint.app.domain.repository.UserRepository
import com.bandymoot.fingerprint.app.domain.usecase.EnrollDeviceUseCase
import com.bandymoot.fingerprint.app.domain.usecase.EnrollUserUseCase
import com.bandymoot.fingerprint.app.domain.usecase.GetAllUsersUseCase
import com.bandymoot.fingerprint.app.domain.usecase.GetAttendanceByUserAndMonthUseCase
import com.bandymoot.fingerprint.app.domain.usecase.GetAttendanceGroupedByDate
import com.bandymoot.fingerprint.app.domain.usecase.GetUserByIdUseCase
import com.bandymoot.fingerprint.app.domain.usecase.InitialSyncUseCase
import com.bandymoot.fingerprint.app.domain.usecase.SyncAttendanceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetAllUsersUseCase(
        repositoryImpl: UserRepositoryImpl
    ): GetAllUsersUseCase {
        return GetAllUsersUseCase(repositoryImpl)
    }

    @Provides
    fun provideGetUserByIdUseCase(
        repositoryImpl: UserRepositoryImpl
    ): GetUserByIdUseCase {
        return GetUserByIdUseCase(repositoryImpl)
    }

    @Provides
    fun provideGetAttendanceGroupedByDate(
        repositoryImpl: AttendanceRepository
    ): GetAttendanceGroupedByDate {
        return GetAttendanceGroupedByDate(repositoryImpl)
    }

    @Provides
    fun provideEnrollUserUseCase(
        enrollmentRepository: EnrollmentRepository
    ): EnrollUserUseCase {
        return EnrollUserUseCase(enrollmentRepository)
    }

    @Provides
    fun provideSyncAttendanceUseCase(
        userRepository: UserRepository,
        attendanceRepository: AttendanceRepository
    ): SyncAttendanceUseCase {
        return SyncAttendanceUseCase(
            userRepository = userRepository,
            attendanceRepository = attendanceRepository
        )
    }

    @Provides
    fun provideInitialSyncUseCase(
        managerRepository: ManagerRepository,
        deviceRepository: DeviceRepository,
        userRepository: UserRepository,
        syncAttendanceUseCase: SyncAttendanceUseCase
    ): InitialSyncUseCase {
        return InitialSyncUseCase(
            managerRepository = managerRepository,
            deviceRepository = deviceRepository,
            userRepository = userRepository,
            syncAttendanceUseCase = syncAttendanceUseCase
        )
    }

    @Provides
    fun provideEnrollDeviceUseCase(
        deviceRepository: DeviceRepository
    ): EnrollDeviceUseCase {
        return EnrollDeviceUseCase(deviceRepository)
    }

    @Provides
    fun provideGetAttendanceByUserAndMonthUseCase(
        attendanceRepository: AttendanceRepository
    ): GetAttendanceByUserAndMonthUseCase {
        return GetAttendanceByUserAndMonthUseCase(
            attendanceRepository = attendanceRepository
        )
    }
}