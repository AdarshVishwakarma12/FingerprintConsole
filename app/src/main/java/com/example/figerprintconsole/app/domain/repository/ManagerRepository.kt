package com.example.figerprintconsole.app.domain.repository

import com.example.figerprintconsole.app.data.repository.RepositoryResult
import com.example.figerprintconsole.app.domain.model.Manager
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface ManagerRepository {
    fun observeAll(): Flow<List<Manager>>
    suspend fun delete(id: UUID): RepositoryResult<Unit>
    suspend fun sync(): RepositoryResult<Unit>
}