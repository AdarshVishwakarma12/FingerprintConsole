package com.bandymoot.fingerprint.app.ui.screen.users.state

import com.bandymoot.fingerprint.app.domain.model.User
import com.bandymoot.fingerprint.app.domain.model.UserDetail

data class UsersUiState (
    val isRefreshing: Boolean = false,
    val listState: UsersListState = UsersListState.Loading,
    val detailUserUiState: DetailUserUiState = DetailUserUiState.Hidden,
    val searchQueryUiState: SearchQueryUiState.Active = SearchQueryUiState.Active(searchQuery = "")
)

// this is less error prone!
// one state at a time
sealed class UsersListState {
    object Loading : UsersListState()
    data class Success(val users: List<User>) : UsersListState()
    data class Error(val error: String) : UsersListState()
}

sealed class DetailUserUiState {
    object Hidden: DetailUserUiState()
    object Loading: DetailUserUiState()
    class Success(val detailUser: UserDetail): DetailUserUiState()
    class Error(val error: String): DetailUserUiState()
}

sealed class SearchQueryUiState() {
    // object InActive: SearchQueryUiState() // We don't need this any more!
    data class Active(val searchQuery: String): SearchQueryUiState()
}