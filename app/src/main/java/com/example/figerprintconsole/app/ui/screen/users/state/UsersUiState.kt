package com.example.figerprintconsole.app.ui.screen.users.state

import com.example.figerprintconsole.app.domain.model.User

sealed class UsersUiState {

    object Loading : UsersUiState()

    data class Success(
        val users: List<User>
    ) : UsersUiState()

    data class Failure(
        val message: String
    ) : UsersUiState()
}
