package com.bandymoot.fingerprint.app.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class NetworkManager @Inject constructor(
    private val applicationContext: Context
) {

    // We need Stateflow, to expose our data!
    private val _networkState: MutableStateFlow<NetworkState> = MutableStateFlow(NetworkState.Unavailable)
    val networkState: StateFlow<NetworkState> get() = _networkState

    // the connectivity manager
    val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // now we need a network callback listener
    val networkCallback = object: ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _networkState.value = NetworkState.Available
        }

        override fun onLost(network: Network) {
            _networkState.value = NetworkState.Unavailable
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            _networkState.value = NetworkState.Losing
        }

        override fun onUnavailable() {
            _networkState.value = NetworkState.Unavailable
        }
    }

    // now we have to register this listener
    init {
        val request = NetworkRequest
            .Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, networkCallback)
    }
}