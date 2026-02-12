package com.bandymoot.fingerprint.app.network

sealed class NetworkState {
    object Available: NetworkState()
    object Unavailable: NetworkState()
    object Losing: NetworkState()
}