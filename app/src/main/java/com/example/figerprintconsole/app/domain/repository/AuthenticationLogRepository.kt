package com.example.figerprintconsole.app.domain.repository

import com.example.figerprintconsole.app.data.repository.RepositoryResult
import com.example.figerprintconsole.app.domain.model.AuthenticationLog
import kotlinx.coroutines.flow.Flow

interface AuthenticationLogRepository {
    fun observeAll(): Flow<List<AuthenticationLog>>
    suspend fun delete(id: String): RepositoryResult
    suspend fun sync(): RepositoryResult
}