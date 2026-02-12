package com.bandymoot.fingerprint.app.di

import android.content.Context
import android.content.SharedPreferences
import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.data.remote.api.ApiServices
import com.bandymoot.fingerprint.app.data.websocket.TestWebSocket
import com.bandymoot.fingerprint.app.network.NetworkManager
import com.bandymoot.fingerprint.app.utils.AppConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    // Network Manager
    @Provides
    @Singleton
    fun provideNetworkManager(
        @ApplicationContext context: Context
    ): NetworkManager {
        return NetworkManager(context)
    }

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

    // Define the Shared Preference
    // ApplicationContext => will be alive until the un-installation of application
    @Provides
    @Singleton
    fun provideSharedPreference(
        @ApplicationContext content: Context
    ): SharedPreferences {
        return content.getSharedPreferences(AppConstant.SHARED_PREF, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideTokenProvider(
        sharedPreferences: SharedPreferences
    ): TokenProvider {
        return TokenProvider(sharedPreferences)
    }
}