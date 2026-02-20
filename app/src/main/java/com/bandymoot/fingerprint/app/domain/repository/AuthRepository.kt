package com.bandymoot.fingerprint.app.domain.repository

import com.bandymoot.fingerprint.app.domain.model.LoginResult

interface AuthRepository {
    suspend fun loginUser(userEmail: String, password: String): LoginResult
    suspend fun loginFakeUserForDemo(userEmail: String, password: String): LoginResult
    suspend fun logout()
}