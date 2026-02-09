package com.bandymoot.fingerprint.app.ui.screen.users.state

import com.bandymoot.fingerprint.app.domain.model.UserDetail

sealed class UserDetailUiState {
    object Loading : UserDetailUiState()
    data class Success(val user: UserDetail) : UserDetailUiState()
    data class Failure(val message: String) : UserDetailUiState()
}