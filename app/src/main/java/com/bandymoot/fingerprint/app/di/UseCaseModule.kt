package com.bandymoot.fingerprint.app.di

import com.bandymoot.fingerprint.app.data.repository.UserRepositoryImpl
import com.bandymoot.fingerprint.app.domain.repository.AttendanceRepository
import com.bandymoot.fingerprint.app.domain.usecase.GetAllUsersUseCase
import com.bandymoot.fingerprint.app.domain.usecase.GetAttendanceGroupedByDate
import com.bandymoot.fingerprint.app.domain.usecase.GetUserByIdUseCase
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
}