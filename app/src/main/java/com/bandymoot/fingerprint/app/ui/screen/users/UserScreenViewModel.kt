package com.bandymoot.fingerprint.app.ui.screen.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.User
import com.bandymoot.fingerprint.app.domain.repository.UserRepository
import com.bandymoot.fingerprint.app.domain.usecase.GetUserByIdUseCase
import com.bandymoot.fingerprint.app.ui.screen.users.event.UsersUiEvent
import com.bandymoot.fingerprint.app.ui.screen.users.state.DetailUserUiState
import com.bandymoot.fingerprint.app.ui.screen.users.state.SearchQueryUiState
import com.bandymoot.fingerprint.app.ui.screen.users.state.UsersListState
import com.bandymoot.fingerprint.app.ui.screen.users.state.UsersUiState
import com.bandymoot.fingerprint.app.utils.AppConstant
import com.bandymoot.fingerprint.app.utils.showSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserScreenViewModel @Inject constructor(
    val userRepository: UserRepository,
    val getUserByIdUseCase: GetUserByIdUseCase,
//    val fakeDataRepository: FakeDataRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UsersUiState())
    val uiState: StateFlow<UsersUiState> = _uiState

    init {
        getAllUsers()
    }

    fun onEvent(event: UsersUiEvent) {
        when(event) {
            is UsersUiEvent.OpenSearch -> { _uiState.update { it.copy(searchQueryUiState = SearchQueryUiState.Active("")) } }
            is UsersUiEvent.CloseSearch -> { _uiState.update { it.copy(searchQueryUiState = SearchQueryUiState.InActive) } }
            is UsersUiEvent.SearchQueryChanged -> { _uiState.update { it.copy(searchQueryUiState = SearchQueryUiState.Active(event.query)) } }
            is UsersUiEvent.OpenUserDetail -> { fetchUserDetail(event.user) }
            is UsersUiEvent.CloseUserDetail -> { _uiState.update { it.copy(detailUserUiState = DetailUserUiState.Hidden) } }
            is UsersUiEvent.ShowSnackBar -> {  }
            is UsersUiEvent.PullToRefresh -> {
                _uiState.update { it.copy(isRefreshing = true) }
                syncDataFromApi()
            }
        }
    }

    fun getAllUsers() {
        userRepository.observeAll()
            .onStart {
                _uiState.update {
                    it.copy(
                        listState = UsersListState.Loading
                    )
                }
            }
            .catch { error ->
                _uiState.update {
                    it.copy(
                        listState = UsersListState.Error(error = error.toString())
                    )
                }
            }
            .onEach { users ->
                _uiState.update {
                    it.copy(
                        listState = UsersListState.Success(users = users)
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun fetchUserDetail(user: User) {
        _uiState.update {
            it.copy(
                detailUserUiState = DetailUserUiState.Loading
            )
        }

        viewModelScope.launch {
            val detailUser = getUserByIdUseCase(user)

            AppConstant.debugMessage("I am at the userScreenViewModel!!, called and get the data: $detailUser")

            when(detailUser) {
                is RepositoryResult.Success -> {
                    _uiState.update {
                        it.copy(
                            detailUserUiState = DetailUserUiState.Success(detailUser.data)
                        )
                    }
                }
                is RepositoryResult.Failed -> {
                    _uiState.update {
                        it.copy(
                            detailUserUiState = DetailUserUiState.Error("Error Loading!")
                        )
                    }
                    showSnackBar(detailUser.throwable.message ?: "Something Went Wrong")
                }
            }
        }
    }

    fun syncDataFromApi() {
        viewModelScope.launch {
            val response = userRepository.sync()
            if(response is RepositoryResult.Failed) { showSnackBar("Failed to fetch Users") }
            delay(3500)
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }
}