package com.bandymoot.fingerprint.app.data.sync

import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.data.local.dao.ManagerDao
import com.bandymoot.fingerprint.app.domain.usecase.InitialSyncUseCase
import com.bandymoot.fingerprint.app.network.NetworkManager
import com.bandymoot.fingerprint.app.network.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class SyncManager @Inject constructor(
    private val initialSyncUseCase: InitialSyncUseCase,
    private val tokenProvider: TokenProvider,
    private val networkManager: NetworkManager,
    private val managerDao: ManagerDao,
) {

    private var syncJob: Job? = null
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    fun startObserving() {
        syncJob?.cancel()
        syncJob = coroutineScope.launch {
            combine(
                tokenProvider.tokenFLow,
                networkManager.networkState
            ) { token, network ->
                val hasToken = !token.isNullOrEmpty()
                val isOnline = network is NetworkState.Available
                hasToken && isOnline
            }
                .distinctUntilChanged()
                .collectLatest { canSync ->
                    if(canSync) {
                        val isDatabaseEmpty = managerDao.getManagerCount() == 0
                        if(isDatabaseEmpty) {
                            initialSyncUseCase.execute()
                        }
                    }
                }
        }
    }
}