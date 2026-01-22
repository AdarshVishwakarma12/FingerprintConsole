package com.example.figerprintconsole.app.data.repository

import com.example.figerprintconsole.app.domain.model.AuditLog
import com.example.figerprintconsole.app.domain.repository.AuditLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuditLogRepositoryImpl @Inject constructor(

): AuditLogRepository {

    override fun observeAll(): Flow<List<AuditLog>> = flow {
        emit(emptyList<AuditLog>())
    }

    override suspend fun delete(id: String): RepositoryResult {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }

    override suspend fun sync(): RepositoryResult {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }
}