package com.example.figerprintconsole.app.domain.repository

import com.example.figerprintconsole.app.data.repository.RepositoryResult
import com.example.figerprintconsole.app.domain.model.Fingerprint
import kotlinx.coroutines.flow.Flow

interface FingerprintRepository {
    fun observeAll(): Flow<List<Fingerprint>>
    suspend fun delete(id: String): RepositoryResult
    suspend fun sync(): RepositoryResult
}