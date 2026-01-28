package com.example.figerprintconsole.app.domain.repository

import com.example.figerprintconsole.app.data.repository.RepositoryResult
import com.example.figerprintconsole.app.domain.model.AuditLog
import kotlinx.coroutines.flow.Flow

interface AuditLogRepository {
    fun observeAll(): Flow<List<AuditLog>>
    suspend fun delete(id: String): RepositoryResult<Unit>
    suspend fun sync(): RepositoryResult<Unit>
}