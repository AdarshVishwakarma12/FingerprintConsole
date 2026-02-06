package com.example.figerprintconsole.app.ui.screen.users.state

import com.example.figerprintconsole.app.domain.model.UserDetail

sealed class UserDetailUiState {
    object Loading : UserDetailUiState()
    data class Success(val user: UserDetail) : UserDetailUiState()
    data class Failure(val message: String) : UserDetailUiState()
}