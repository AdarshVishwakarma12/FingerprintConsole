package com.bandymoot.fingerprint.app.data.repository

import com.bandymoot.fingerprint.app.domain.model.Manager
import com.bandymoot.fingerprint.app.domain.repository.ManagerRepository
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