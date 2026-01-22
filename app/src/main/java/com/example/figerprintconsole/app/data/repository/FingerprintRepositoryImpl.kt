package com.example.figerprintconsole.app.data.repository

import com.example.figerprintconsole.app.domain.model.Fingerprint
import com.example.figerprintconsole.app.domain.repository.FingerprintRepository
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

    override suspend fun delete(id: String): RepositoryResult {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }

    override suspend fun sync(): RepositoryResult {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }
}