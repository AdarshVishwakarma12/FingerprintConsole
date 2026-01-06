package com.example.figerprintconsole.app.di

import com.example.figerprintconsole.app.data.repository.EnrollmentRepositoryImpl
import com.example.figerprintconsole.app.data.websocket.EnrollmentSocketDataSourceImpl
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
}