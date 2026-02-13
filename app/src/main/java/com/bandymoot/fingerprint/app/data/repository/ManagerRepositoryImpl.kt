package com.bandymoot.fingerprint.app.data.repository

import androidx.room.withTransaction
import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.data.local.dao.ManagerDao
import com.bandymoot.fingerprint.app.data.local.dao.OrganizationDao
import com.bandymoot.fingerprint.app.data.mapper.toEntity
import com.bandymoot.fingerprint.app.data.remote.api.ApiServices
import com.bandymoot.fingerprint.app.data.remote.safeApiCall
import com.bandymoot.fingerprint.app.di.AppDatabase
import com.bandymoot.fingerprint.app.domain.model.Manager
import com.bandymoot.fingerprint.app.domain.repository.ManagerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class ManagerRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices,
    private val tokenProvider: TokenProvider,
    private val organizationDao: OrganizationDao,
    private val managerDao: ManagerDao,
    private val appDatabase: AppDatabase
): ManagerRepository {

    override fun observeAll(): Flow<List<Manager>> = flow {
        emit(emptyList())
    }

    override suspend fun delete(id: UUID): RepositoryResult<Unit> {
        return RepositoryResult.Failed(Exception("Not Implemented"))
    }

    override suspend fun sync(): RepositoryResult<Unit> {
        // We trigger two api's over here!
        //  Organisation -> Manager

        val token = tokenProvider.tokenFLow.value ?: return RepositoryResult.Failed(Exception("Token Not Found"))

        val managerResult = safeApiCall { apiServices.getManagerDetail(token) }

        if(managerResult is RepositoryResult.Failed) return managerResult

        val managerEntity = (managerResult as RepositoryResult.Success).data.data.toEntity()
        val organizationEntity = managerResult.data.data.organization.toEntity()

        return try {
            appDatabase.withTransaction {
                organizationDao.upsert(organizationEntity)
                managerDao.upsert(managerEntity)
            }
            RepositoryResult.Success(Unit)
        } catch (e: Exception) {
            RepositoryResult.Failed(e)
        }
    }
}