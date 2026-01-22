package com.example.figerprintconsole.app.ui.screen.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.figerprintconsole.app.data.repository.FakeDataRepository
import com.example.figerprintconsole.app.domain.model.User
import com.example.figerprintconsole.app.domain.usecase.GetAllUsersUseCase
import com.example.figerprintconsole.app.domain.usecase.GetUserByIdUseCase
import com.example.figerprintconsole.app.ui.screen.users.event.UsersScreenEvent
import com.example.figerprintconsole.app.ui.screen.users.state.UserDetailUiState
import com.example.figerprintconsole.app.ui.screen.users.state.UsersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserScreenViewModel @Inject constructor(
    val getAllUserUsersUseCase: GetAllUsersUseCase,
    val getUserByIdUseCase: GetUserByIdUseCase,
    val fakeDataRepository: FakeDataRepository
): ViewModel() {

    private var isInitialLoaded: Boolean = false

    private val _uiStateAllUsers = MutableStateFlow<UsersUiState>(UsersUiState.Loading)
    val uiStateAllUsers: StateFlow<UsersUiState> = _uiStateAllUsers

    private val _uiStateUserById = MutableStateFlow<UserDetailUiState>(UserDetailUiState.Loading)
    val uiStateUserById: StateFlow<UserDetailUiState> = _uiStateUserById

    init {
        viewModelScope.launch {
            fakeDataRepository.seedMassiveData()
        }
    }

    fun onEvent(event: UsersScreenEvent) {
        when(event) {
            is UsersScreenEvent.GetAllUsers -> { getAllUsers() }
            is UsersScreenEvent.GetUserById -> { getUserById(event.user) }
        }
    }

    fun getAllUsers() {

        // Set State as Loading
        _uiStateAllUsers.value = UsersUiState.Loading

        // Get Response from the UseCase
        viewModelScope.launch {
            val response = getAllUserUsersUseCase()

            response.collect { it ->
                _uiStateAllUsers.value = UsersUiState.Success(it)
            }


        }
    }

    fun getUserById(user: User) {

        // Set State as Loading
        _uiStateUserById.value = UserDetailUiState.Loading

//        viewModelScope.launch {
//            val response = getUserByIdUseCase(user)
//
//            response.onSuccess { user ->
//                _uiStateUserById.value = UserDetailUiState.Success(user)
//            }.onFailure { it ->
//                _uiStateUserById.value = UserDetailUiState.Failure(it.message ?: "Unknown Error")
//            }
//        }

    }
}