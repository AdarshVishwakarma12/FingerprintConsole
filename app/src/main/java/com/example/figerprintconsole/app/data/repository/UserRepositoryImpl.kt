package com.example.figerprintconsole.app.data.repository

import com.example.figerprintconsole.app.data.local.dao.UserDao
import com.example.figerprintconsole.app.data.mapper.toDomain
import com.example.figerprintconsole.app.data.remote.NetworkException
import com.example.figerprintconsole.app.data.remote.api.ApiServices
import com.example.figerprintconsole.app.di.AppDatabase
import com.example.figerprintconsole.app.domain.model.User
import com.example.figerprintconsole.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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

    override suspend fun delete(id: UUID): RepositoryResult {
        // Call the ApiService
        return RepositoryResult.Failed(Exception("Method not Implemented"))
    }

    override suspend fun sync(): RepositoryResult {
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