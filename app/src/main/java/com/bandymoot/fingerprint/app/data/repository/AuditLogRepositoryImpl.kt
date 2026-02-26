package com.bandymoot.fingerprint.app.data.repository

import com.bandymoot.fingerprint.app.domain.model.AuditLog
import com.bandymoot.fingerprint.app.domain.model.RepositoryResult
import com.bandymoot.fingerprint.app.domain.repository.AuditLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuditLogRepositoryImpl @Inject constructor(

): AuditLogRepository {

    override fun observeAll(): Flow<List<AuditLog>> = flow {
        emit(emptyList<AuditLog>())
    }

    override suspend fun delete(id: String): RepositoryResult<Unit> {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }

    override suspend fun sync(): RepositoryResult<Unit> {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }
}