package com.bandymoot.fingerprint.app.data.repository

import com.bandymoot.fingerprint.app.data.local.dao.UserDao
import com.bandymoot.fingerprint.app.data.mapper.toDomain
import com.bandymoot.fingerprint.app.data.remote.NetworkException
import com.bandymoot.fingerprint.app.data.remote.api.ApiServices
import com.bandymoot.fingerprint.app.di.AppDatabase
import com.bandymoot.fingerprint.app.domain.model.User
import com.bandymoot.fingerprint.app.domain.model.UserDetail
import com.bandymoot.fingerprint.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices,
    private val userDao: UserDao,
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
        // Call the ApiService
        return RepositoryResult.Failed(Exception("Method not Implemented"))
    }
}

private fun mapHttpException(response: Response<*>?): NetworkException {
    val code = response?.code() ?: -1
    val errorBody = response?.errorBody()?.string()

    return when (code) {
        in 400..499 -> NetworkException.ClientError(code, errorBody)
        in 500..599 -> NetworkException.ServerError(code)
        else -> NetworkException.Unknown(Exception("HTTP error $code"))
    }
}