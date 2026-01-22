package com.example.figerprintconsole.app.data.repository

sealed class RepositoryResult {
    object Success: RepositoryResult()
    data class Failed(val throwable: Throwable): RepositoryResult()
}