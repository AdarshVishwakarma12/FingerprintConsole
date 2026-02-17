package com.bandymoot.fingerprint.app.di

import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.data.local.dao.ManagerDao
import com.bandymoot.fingerprint.app.data.sync.SyncManager
import com.bandymoot.fingerprint.app.domain.usecase.InitialSyncUseCase
import com.bandymoot.fingerprint.app.network.NetworkManager
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SyncManagerModule {

    fun provideSyncManager(
        initialSyncUseCase: InitialSyncUseCase,
        tokenProvider: TokenProvider,
        networkManager: NetworkManager,
        managerDao: ManagerDao,
    ): SyncManager {
        return SyncManager(
            initialSyncUseCase = initialSyncUseCase,
            tokenProvider = tokenProvider,
            networkManager = networkManager,
            managerDao = managerDao
        )
    }
}