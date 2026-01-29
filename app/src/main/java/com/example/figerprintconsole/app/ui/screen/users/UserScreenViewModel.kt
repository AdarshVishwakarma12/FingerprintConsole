package com.example.figerprintconsole.app.ui.screen.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.figerprintconsole.app.data.repository.RepositoryResult
import com.example.figerprintconsole.app.domain.model.User
import com.example.figerprintconsole.app.domain.repository.UserRepository
import com.example.figerprintconsole.app.domain.usecase.GetUserByIdUseCase
import com.example.figerprintconsole.app.ui.screen.users.event.UsersUiEvent
import com.example.figerprintconsole.app.ui.screen.users.state.DetailUserUiState
import com.example.figerprintconsole.app.ui.screen.users.state.SearchQueryUiState
import com.example.figerprintconsole.app.ui.screen.users.state.UsersListState
import com.example.figerprintconsole.app.ui.screen.users.state.UsersUiState
import com.example.figerprintconsole.app.utils.AppConstant
import com.example.figerprintconsole.app.utils.showSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
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

//    init {
//        viewModelScope.launch {
//            fakeDataRepository.seedMassiveData()
//        }
//    }

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

                    AppConstant.debugMessage("!!!SHOWING SNACK BAR NOW!!! ViewModel")
                    showSnackBar(detailUser.throwable.message ?: "Something Went Wrong")
                }
            }
        }
    }
}