package com.bandymoot.fingerprint.app.domain.model

sealed class LoginResult {
    class Success(
        val isAuthenticated: Boolean,
    ) : LoginResult()

    class Failed(
        val error: AppError
    ) : LoginResult()
}