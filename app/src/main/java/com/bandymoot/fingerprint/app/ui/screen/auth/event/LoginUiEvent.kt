package com.bandymoot.fingerprint.app.ui.screen.auth.event

sealed interface LoginUiEvent {

    data class EmailChanged(val value: String) : LoginUiEvent
    data class PasswordChanged(val value: String) : LoginUiEvent

    object CheckEmailValidity : LoginUiEvent
    object CheckPasswordValidity : LoginUiEvent

    object LoginClicked : LoginUiEvent

    object ClearError : LoginUiEvent
}