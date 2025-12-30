package com.example.figerprintconsole.app.data.repository

import com.example.figerprintconsole.app.data.mapper.toDomain
import com.example.figerprintconsole.app.data.remote.NetworkException
import com.example.figerprintconsole.app.data.remote.api.ApiServices
import com.example.figerprintconsole.app.domain.model.User
import com.example.figerprintconsole.app.domain.repository.UserRepository
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices
): UserRepository {

    override suspend fun getAllUsers(): Result<List<User>> {
        return try {
            val response = apiServices.getAllUsers()

            if (!response.isSuccessful) {
                return Result.failure(mapHttpException(response))
            }

            val body = response.body() ?: return Result.failure(NetworkException.EmptyBody)

            val users = body.data

            val domainUsers = users.map { it.toDomain() }

            Result.success(domainUsers)

        } catch (_: IOException) {
            // Network / no internet
            Result.failure(NetworkException.NoInternet)

        } catch (e: HttpException) {
            // Retrofit HTTP exception
            Result.failure(mapHttpException(e.response()))

        } catch (e: Exception) {
            Result.failure(NetworkException.Unknown(e))
        }
    }

    override suspend fun getUserById(userId: Int): Result<User> {
        try {
            val response = apiServices.getUserById(userId = userId)

            if (!response.isSuccessful) {
                return Result.failure(mapHttpException(response))
            }

            val body = response.body() ?: return Result.failure(NetworkException.EmptyBody)

            val user = body.data

            val domainUser = user.toDomain()

            return Result.success(domainUser)

        } catch (_: IOException) {
            return Result.failure(NetworkException.NoInternet)
        } catch (e: HttpException) {
            return Result.failure(mapHttpException(e.response()))
        } catch (e: Exception) {
            return Result.failure(NetworkException.Unknown(e))
        }
    }

    override suspend fun deleteUserById(userId: Int): Result<Boolean> {
        return Result.failure(Exception("Api not calling"))
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