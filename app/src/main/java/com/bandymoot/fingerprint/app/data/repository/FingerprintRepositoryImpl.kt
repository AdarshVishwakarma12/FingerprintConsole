package com.bandymoot.fingerprint.app.data.repository

import com.bandymoot.fingerprint.app.domain.model.Fingerprint
import com.bandymoot.fingerprint.app.domain.model.RepositoryResult
import com.bandymoot.fingerprint.app.domain.repository.FingerprintRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FingerprintRepositoryImpl @Inject constructor(

): FingerprintRepository {

    override fun observeAll(): Flow<List<Fingerprint>> = flow {
        emit(emptyList<Fingerprint>())
    }.catch { e ->

    }

    override suspend fun delete(id: String): RepositoryResult<Unit> {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }

    override suspend fun sync(): RepositoryResult<Unit> {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }
}