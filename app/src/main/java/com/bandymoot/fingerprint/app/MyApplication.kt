package com.bandymoot.fingerprint.app

import android.app.Application
import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.data.socket.SocketManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {
    @Inject lateinit var tokenProvider: TokenProvider
    override fun onCreate() {
        super.onCreate()
        SocketManager.observeToken(tokenProvider.tokenFLow)
    }
}