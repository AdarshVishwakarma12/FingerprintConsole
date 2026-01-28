package com.example.figerprintconsole.app.data.repository

import com.example.figerprintconsole.app.domain.model.Manager
import com.example.figerprintconsole.app.domain.repository.ManagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class ManagerRepositoryImpl @Inject constructor(

): ManagerRepository {

    override fun observeAll(): Flow<List<Manager>> = flow {
        emit(emptyList<Manager>())
    }

    override suspend fun delete(id: UUID): RepositoryResult<Unit> {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }

    override suspend fun sync(): RepositoryResult<Unit> {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }
}