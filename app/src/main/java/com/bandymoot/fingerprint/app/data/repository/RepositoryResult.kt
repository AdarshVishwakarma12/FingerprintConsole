package com.bandymoot.fingerprint.app.data.repository

sealed class RepositoryResult<out T> {
    data class Success<out T>(val data: T) : RepositoryResult<T>()
    data class Failed(val throwable: Throwable) : RepositoryResult<Nothing>()
}
