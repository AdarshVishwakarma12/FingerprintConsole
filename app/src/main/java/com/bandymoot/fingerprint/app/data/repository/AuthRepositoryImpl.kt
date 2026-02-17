package com.bandymoot.fingerprint.app.data.repository

import com.bandymoot.fingerprint.app.data.dto.LoginRequest
import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.data.mapper.toAppError
import com.bandymoot.fingerprint.app.data.remote.api.ApiServices
import com.bandymoot.fingerprint.app.di.AppDatabase
import com.bandymoot.fingerprint.app.domain.model.AppError
import com.bandymoot.fingerprint.app.domain.model.LoginResult
import com.bandymoot.fingerprint.app.domain.model.fromHttpCode
import com.bandymoot.fingerprint.app.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiServices: ApiServices,
    private val tokenProvider: TokenProvider,
    private val appDatabase: AppDatabase
): AuthRepository {

    override suspend fun loginUser(userEmail: String, password: String): LoginResult {

        try {
            val request = LoginRequest(userEmail, password)
            val response = apiServices.loginUser(request)

            if (!response.isSuccessful) {
                return LoginResult.Failed(
                    AppError.fromHttpCode(response.code())
                )
            }

            val body = response.body()
                ?: return LoginResult.Failed(
                    AppError.Unknown("Empty body")
                )

            tokenProvider.saveAccessToken(body.data)
            tokenProvider.saveUserEmail(email = userEmail)
            tokenProvider.saveLoginTimestamp()

            return LoginResult.Success(isAuthenticated = true)

        } catch (e: Exception) {
            return LoginResult.Failed(e.toAppError())
        }
    }

    override suspend fun logout() {
        tokenProvider.removeAllToken()
        appDatabase.clearAllTables()
    }
}