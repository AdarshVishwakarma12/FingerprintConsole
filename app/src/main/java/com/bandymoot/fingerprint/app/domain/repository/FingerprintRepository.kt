package com.bandymoot.fingerprint.app.domain.repository

import com.bandymoot.fingerprint.app.domain.model.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.Fingerprint
import kotlinx.coroutines.flow.Flow

interface FingerprintRepository {
    fun observeAll(): Flow<List<Fingerprint>>
    suspend fun delete(id: String): RepositoryResult<Unit>
    suspend fun sync(): RepositoryResult<Unit>
}