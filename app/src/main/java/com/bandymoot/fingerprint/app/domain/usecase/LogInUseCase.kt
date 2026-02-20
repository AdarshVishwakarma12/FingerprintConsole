package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.domain.model.AppError
import com.bandymoot.fingerprint.app.domain.model.LoginResult
import com.bandymoot.fingerprint.app.domain.repository.AuthRepository
import com.bandymoot.fingerprint.app.domain.validation.EmailValidator
import com.bandymoot.fingerprint.app.domain.validation.PasswordValidator
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): LoginResult {

        // Validate Input
        // Changed from Android Library to Pure Kotlin -> Easier testing
        if(!EmailValidator.isValid(email) || !PasswordValidator.isValid(password)) {
            return LoginResult.Failed(AppError.InValidDataEntered())
        }

        // YUP::This is for Deployment phase too! Cause we may need to show DEMO.
        return if(email == "demoapplication@gmail.com" && password == "securedDemoApplication") {
            authRepository.loginFakeUserForDemo(userEmail = email, password = password)
        } else {
            authRepository.loginUser(userEmail = email, password = password)
        }
    }
}