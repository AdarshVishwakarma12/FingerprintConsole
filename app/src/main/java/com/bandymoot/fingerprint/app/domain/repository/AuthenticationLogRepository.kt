package com.bandymoot.fingerprint.app.domain.repository

import com.bandymoot.fingerprint.app.domain.model.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.AuthenticationLog
import kotlinx.coroutines.flow.Flow

interface AuthenticationLogRepository {
    fun observeAll(): Flow<List<AuthenticationLog>>
    suspend fun delete(id: String): RepositoryResult<Unit>
    suspend fun sync(): RepositoryResult<Unit>
}