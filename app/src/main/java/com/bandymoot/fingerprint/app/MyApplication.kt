package com.bandymoot.fingerprint.app

import android.app.Application
import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.data.socket.SocketManager
import com.bandymoot.fingerprint.app.data.sync.SyncManager
import com.bandymoot.fingerprint.app.network.NetworkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {
    @Inject lateinit var tokenProvider: TokenProvider
    @Inject lateinit var networkManager: NetworkManager
    @Inject lateinit var syncManager: SyncManager
    override fun onCreate() {
        super.onCreate()
        SocketManager.observeToken(tokenProvider.tokenFLow, networkManager.networkState)

        syncManager.startObserving()
    }
}