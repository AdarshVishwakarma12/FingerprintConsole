package com.bandymoot.fingerprint.app.ui.screen.users.event

import com.bandymoot.fingerprint.app.domain.model.User

sealed interface UsersUiEvent {
    data class ShowSnackBar(val message: String) : UsersUiEvent
    object OpenSearch: UsersUiEvent
    object CloseSearch: UsersUiEvent
    data class SearchQueryChanged(val query: String) : UsersUiEvent
    data class OpenUserDetail(val user: User) : UsersUiEvent
    object CloseUserDetail : UsersUiEvent
    object PullToRefresh: UsersUiEvent
}