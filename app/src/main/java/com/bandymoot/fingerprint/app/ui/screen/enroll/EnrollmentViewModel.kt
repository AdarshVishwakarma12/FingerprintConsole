package com.bandymoot.fingerprint.app.ui.screen.enroll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.data.repository.EnrollmentRepositoryImpl
import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.data.socket.SocketManager
import com.bandymoot.fingerprint.app.domain.model.EnrollmentSocketEvent
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser
import com.bandymoot.fingerprint.app.domain.usecase.EnrollUserUseCase
import com.bandymoot.fingerprint.app.ui.screen.enroll.event.EnrollScreenEvent
import com.bandymoot.fingerprint.app.ui.screen.enroll.state.EnrollmentScreenState
import com.bandymoot.fingerprint.app.ui.screen.enroll.state.EnrollmentState
import com.bandymoot.fingerprint.app.utils.AppConstant
import com.bandymoot.fingerprint.app.utils.showSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnrollmentViewModel @Inject constructor(
    private val enrollmentRepositoryImpl: EnrollmentRepositoryImpl,
    private val enrollUserUseCase: EnrollUserUseCase
): ViewModel() {

    // Ensure ONE SOCKET!
    private var socketJob: Job? = null

    private val _uiState = MutableStateFlow(EnrollmentState())
    val uiState: StateFlow<EnrollmentState> = _uiState

    // The state will help use in tracking events - and handle viewModel!
    private val _activeWebsocketState = MutableStateFlow<EnrollmentSocketEvent>(EnrollmentSocketEvent.IDLE)

    init { observeWebSocketState() }

    fun onEvent(event: EnrollScreenEvent) {
        when(event) {
            is EnrollScreenEvent.StartEnrollment -> {
                _uiState.update {
                    it.copy(isLoading = true)
                }
                checkSocketConnection()
            }
            is EnrollScreenEvent.ValidateUserInfoAndStartBiometric -> {
                viewModelScope.launch {
                    val response = enrollUserUseCase(_uiState.value.userEnrollInfo)
                    when(response) {
                        is RepositoryResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    currentStep = 2,
                                    enrollmentProgress = .25f,
                                    enrollmentScreenState = EnrollmentScreenState.EnrollingStepOne
                                )
                            }
                        }
                        is RepositoryResult.Failed -> {
                            _uiState.update { it.copy(errorMessage = "Failed to start enrollment") }
                            showSnackBar("Failed to start enrollment")
                        }
                    }
                }
            }
            is EnrollScreenEvent.CheckSocketConnection -> { checkSocketConnection() }
            is EnrollScreenEvent.CANCEL -> { }
            is EnrollScreenEvent.TextFieldInput -> { _uiState.update { it.copy(userEnrollInfo = event.newEnrollUser) } }
            is EnrollScreenEvent.ResetTextFieldInput -> { _uiState.update { it.copy(userEnrollInfo = NewEnrollUser()) } }
            is EnrollScreenEvent.Verify -> { } // check the biometric
            is EnrollScreenEvent.Completed -> { } // navigate back
            else -> { }
        }
    }

    // There should be only one websocket opened!
    fun checkSocketConnection() {
        AppConstant.debugMessage("WS: Connecting to Socket observer!", "WS")

        if(!SocketManager.hasActiveConnection()) {

            AppConstant.debugMessage("WS ERROR", "WS")

            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorMessage = "Failed to Connect Socket"
                )
            }
        } else {
            AppConstant.debugMessage("WS: ARRIVED ONE MESSAGE!", "WS")

            _uiState.update {
                it.copy(
                    currentStep = 1,
                    enrollmentProgress = .10f,
                    isLoading = false,
                    isEnrolling = true,
                    enrollmentScreenState = EnrollmentScreenState.UserInput
                )
            }
        }
    }

    fun showError() {
        showSnackBar("Failed to Connect")
        _uiState.update {
            it.copy(
                errorMessage = "Failed to Enroll User"
            )
        }
    }

    private fun observeWebSocketState() {
        viewModelScope.launch {
            _activeWebsocketState.collect { event ->
                when (event) {
                    is EnrollmentSocketEvent.IDLE -> { }

                    is EnrollmentSocketEvent.Connected -> { }
                    is EnrollmentSocketEvent.EnrollPending -> { }
                    is EnrollmentSocketEvent.EnrollSuccess -> {
                        _uiState.update {
                            it.copy(
                                currentStep = 4,
                                enrollmentProgress = .90f,
                                enrollmentScreenState = EnrollmentScreenState.Enrolled
                            )
                        }
                    }
                    is EnrollmentSocketEvent.AttendanceEvent -> { }
                    is EnrollmentSocketEvent.Disconnected -> { showError() }
                    is EnrollmentSocketEvent.VerificationFailed -> { showError() }
                    is EnrollmentSocketEvent.Error -> { showError() }
                }
            }
        }
    }
}