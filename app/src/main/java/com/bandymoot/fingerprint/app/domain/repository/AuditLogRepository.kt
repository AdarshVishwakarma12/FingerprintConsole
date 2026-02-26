package com.bandymoot.fingerprint.app.domain.repository

import com.bandymoot.fingerprint.app.domain.model.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.AuditLog
import kotlinx.coroutines.flow.Flow

interface AuditLogRepository {
    fun observeAll(): Flow<List<AuditLog>>
    suspend fun delete(id: String): RepositoryResult<Unit>
    suspend fun sync(): RepositoryResult<Unit>
}