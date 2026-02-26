package com.bandymoot.fingerprint.app.domain.model

sealed class RepositoryResultAdvanced<out T> {
    data class Success<out T>(val data: T): RepositoryResultAdvanced<T>()
    data class PartialSuccess<out T>(val data: T, val errorMessage: String): RepositoryResultAdvanced<T>()
    data class Failed(val throwable: Throwable, val descriptiveError: String? = null): RepositoryResultAdvanced<Nothing>()
}