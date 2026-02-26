package com.bandymoot.fingerprint.app.data.repository

import com.bandymoot.fingerprint.app.domain.model.AuthenticationLog
import com.bandymoot.fingerprint.app.domain.model.RepositoryResult
import com.bandymoot.fingerprint.app.domain.repository.AuthenticationLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthenticationLogRepositoryImpl @Inject constructor(

): AuthenticationLogRepository {

    override fun observeAll(): Flow<List<AuthenticationLog>>  = flow {
        emit(emptyList<AuthenticationLog>())
    }.catch { e ->

    }

    override suspend fun delete(id: String): RepositoryResult<Unit> {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }

    override suspend fun sync(): RepositoryResult<Unit> {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }
}