package com.bandymoot.fingerprint.app.data.repository

import androidx.room.withTransaction
import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.data.local.dao.UserDao
import com.bandymoot.fingerprint.app.data.mapper.toDomain
import com.bandymoot.fingerprint.app.data.mapper.toEntity
import com.bandymoot.fingerprint.app.data.remote.api.ApiServices
import com.bandymoot.fingerprint.app.data.remote.safeApiCall
import com.bandymoot.fingerprint.app.di.AppDatabase
import com.bandymoot.fingerprint.app.domain.model.User
import com.bandymoot.fingerprint.app.domain.model.UserDetail
import com.bandymoot.fingerprint.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices,
    private val userDao: UserDao,
    private val tokenProvider: TokenProvider,
    private val appDatabase: AppDatabase
): UserRepository {

    override fun observeAll(): Flow<List<User>> = flow {
        // Read from the database
        userDao.getAll().collect { users ->
            emit(
                users.map { user -> user.toDomain() }
            )
        }
    }.catch { e ->
        // Emit the AppError!
    }

    override suspend fun findDetailUserById(employeeCode: String): RepositoryResult<UserDetail> {
        try {
            val userDetail = userDao.getUserWithDetailById(employeeCode)

            return RepositoryResult.Success(userDetail.toDomain())
        } catch (e: Exception) {
            return RepositoryResult.Failed(e)
        }
    }

    override suspend fun findDetailUserByServerId(userServerId: String): RepositoryResult<UserDetail> {
        try {
            val userDetail = userDao.getUserWithDetailByServerId(serverId = userServerId)

            return RepositoryResult.Success(userDetail.toDomain())
        } catch (e: Exception) {
            return RepositoryResult.Failed(e)
        }
    }

    override suspend fun delete(id: UUID): RepositoryResult<Unit> {
        // Call the ApiService
        return RepositoryResult.Failed(Exception("Method not Implemented"))
    }

    override suspend fun sync(): RepositoryResult<Unit> {
        return try {
            // Call the ApiService
            val tokenValue = tokenProvider.tokenFLow.value ?: return RepositoryResult.Failed(Exception("Token Not Exist"))
            val response = safeApiCall { apiServices.getAllUsers(tokenValue) }

            if(response is RepositoryResult.Failed) return response

            val userEntity = (response as RepositoryResult.Success).data.data

            appDatabase.withTransaction {
                userEntity.map {
                    userDao.upsert(it.toEntity())
                }
            }
            RepositoryResult.Success(Unit)
        } catch (e: Exception) {
            RepositoryResult.Failed(e)
        }
    }
}