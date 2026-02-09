package com.bandymoot.fingerprint.app.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.domain.model.LoginResult
import com.bandymoot.fingerprint.app.domain.usecase.LogInUseCase
import com.bandymoot.fingerprint.app.ui.screen.auth.event.LoginUiEvent
import com.bandymoot.fingerprint.app.ui.screen.auth.state.LoginUiState
import com.bandymoot.fingerprint.app.utils.AppConstant
import com.bandymoot.fingerprint.app.utils.isValidEmail
import com.bandymoot.fingerprint.app.utils.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val logInUseCase: LogInUseCase
): ViewModel() {

    @Suppress("PropertyName")
    val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun observeUiEvent(event: LoginUiEvent) {
        when(event) {
            is LoginUiEvent.CheckEmailValidity -> {
                _uiState.update {
                    val value = uiState.value.email
                    it.copy(
                        isEmailValid = value.isValidEmail(),
                        showEmailError = value.isNotBlank() && !value.isValidEmail()
                    )
                }
            }

            is LoginUiEvent.CheckPasswordValidity -> {
                _uiState.update {
                    val value = uiState.value.password
                    it.copy(
                        isPasswordValid = value.isValidPassword(),
                        showPasswordError = value.isNotBlank() && !value.isValidPassword()
                    )
                }
            }

            is LoginUiEvent.LoginClicked -> { tryAuthenticate() }

            is LoginUiEvent.EmailChanged -> {
                _uiState.update { it ->
                    it.copy(
                        email = event.value,
                        isEmailValid = event.value.isValidEmail(),
                        showEmailError = false
                    )
                }
            }

            is LoginUiEvent.PasswordChanged -> {
                _uiState.update { it ->
                    it.copy(
                        password = event.value,
                        isPasswordValid = event.value.isValidPassword(),
                        showPasswordError = false
                    )
                }
            }

            is LoginUiEvent.ClearError -> {
                clearErrorText()
            }
        }
    }

    private fun tryAuthenticate() {
        viewModelScope.launch {

            // Show Loading
            _uiState.update { it.copy(isLoading = true, authError = null) }

            AppConstant.debugMessage("Loading State", "SYSTEM")

            // delay add a little bit of perfect UX
            delay(1500)

            val result = logInUseCase(uiState.value.email, uiState.value.password)

            _uiState.update { currentState ->
                when (result) {
                    is LoginResult.Failed -> {
                        currentState.copy(
                            isLoading = false,
                            authError = result.error
                        )
                    }
                    is LoginResult.Success -> {
                        currentState.copy(
                            isLoading = false,
                            authError = null
                        )
                    }
                }
            }
        }
    }

    // Error should be clear after some amount of time
    // or activity detach!
    private fun clearErrorText() {
        _uiState.update {
            it.copy(authError = null)
        }
    }

    // Saving password for too long is not good practice!
    private fun clearPasswordText() {
        _uiState.update {
            it.copy(password = "")
        }
    }

    override fun onCleared() {
        super.onCleared()
        clearErrorText()
        clearPasswordText()
    }
}