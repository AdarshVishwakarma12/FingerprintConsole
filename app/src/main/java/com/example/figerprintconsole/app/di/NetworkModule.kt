package com.example.figerprintconsole.app.di

import com.example.figerprintconsole.app.data.remote.api.ApiServices
import com.example.figerprintconsole.app.data.websocket.EnrollmentSocketDataSourceImpl
import com.example.figerprintconsole.app.data.websocket.TestWebSocket
import com.example.figerprintconsole.app.domain.model.EnrollmentSocketEvent
import com.example.figerprintconsole.app.utils.AppConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // TestWebSocket
    @Provides
    @Singleton
    fun provideTestWebsocket(
        client: OkHttpClient,
        request: Request
    ): TestWebSocket {
        return TestWebSocket(
            client = client,
            request = request
        )
    }

    @Provides
    @Singleton
    fun provideRequest(): Request {
        return Request.Builder()
            .url(AppConstant.WEB_SOCKET_URL)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(AppConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }
}