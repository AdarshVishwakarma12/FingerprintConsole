package com.bandymoot.fingerprint.app.domain.model

sealed class RepositoryResult<out T> {
    data class Success<out T>(val data: T) : RepositoryResult<T>()
    data class Failed(val throwable: Throwable, val descriptiveError: String? = null) : RepositoryResult<Nothing>()
}