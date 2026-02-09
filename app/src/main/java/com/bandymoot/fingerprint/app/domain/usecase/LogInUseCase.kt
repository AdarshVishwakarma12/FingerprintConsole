package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.domain.model.AppError
import com.bandymoot.fingerprint.app.domain.model.LoginResult
import com.bandymoot.fingerprint.app.domain.repository.AuthRepository
import com.bandymoot.fingerprint.app.utils.isValidEmail
import com.bandymoot.fingerprint.app.utils.isValidPassword
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): LoginResult {
        if(!email.isValidEmail() || !password.isValidPassword()) {
            return LoginResult.Failed(AppError.InValidDataEntered())
        }

        // Validate Rate Limiting!

        return authRepository.loginUser(email, password)
    }
}