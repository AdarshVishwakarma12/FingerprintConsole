package com.example.figerprintconsole.app.domain.repository

import com.example.figerprintconsole.app.data.repository.RepositoryResult
import com.example.figerprintconsole.app.domain.model.Organization
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface OrganizationRepository {
    fun observeAll(): Flow<List<Organization>> // Single Tenate
    suspend fun delete(id: UUID): UUID
    suspend fun sync(): RepositoryResult
}