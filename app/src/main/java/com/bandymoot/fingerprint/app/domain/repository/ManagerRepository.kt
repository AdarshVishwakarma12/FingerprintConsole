package com.bandymoot.fingerprint.app.domain.repository

import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.Manager
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ManagerRepository {
    fun observeAll(): Flow<List<Manager>>
    suspend fun delete(id: UUID): RepositoryResult<Unit>
    suspend fun sync(): RepositoryResult<Unit>
}