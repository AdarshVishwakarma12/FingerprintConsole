package com.example.figerprintconsole.app.ui.screen.users.state

import com.example.figerprintconsole.app.domain.model.User

sealed class UserDetailUiState {
    object Loading : UserDetailUiState()
    data class Success(val user: User) : UserDetailUiState()
    data class Failure(val message: String) : UserDetailUiState()
}