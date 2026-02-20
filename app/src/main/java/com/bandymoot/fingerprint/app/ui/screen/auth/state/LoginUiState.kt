package com.bandymoot.fingerprint.app.ui.screen.auth.state

import com.bandymoot.fingerprint.app.domain.model.AppError

data class LoginUiState(
    val email: String = "demoapplication@gmail.com",
    val password: String = "securedDemoApplication",

    val isLoading: Boolean = false,

    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,

    val showEmailError: Boolean = false,
    val showPasswordError: Boolean = false,

    val authError: AppError? = null
)
