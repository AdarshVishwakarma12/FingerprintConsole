package com.example.figerprintconsole.app.ui.screen.users.event

import com.example.figerprintconsole.app.domain.model.User

sealed interface UsersUiEvent {
    data class ShowSnackBar(val message: String) : UsersUiEvent
    object OpenSearch: UsersUiEvent
    object CloseSearch: UsersUiEvent
    data class SearchQueryChanged(val query: String) : UsersUiEvent
    data class OpenUserDetail(val user: User) : UsersUiEvent
    object CloseUserDetail : UsersUiEvent
}