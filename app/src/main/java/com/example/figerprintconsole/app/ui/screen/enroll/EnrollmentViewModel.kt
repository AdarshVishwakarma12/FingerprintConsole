package com.example.figerprintconsole.app.ui.screen.enroll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.figerprintconsole.app.data.repository.EnrollmentRepositoryImpl
import com.example.figerprintconsole.app.domain.model.EnrollmentSocketEvent
import com.example.figerprintconsole.app.domain.model.NewEnrollUser
import com.example.figerprintconsole.app.ui.screen.enroll.event.EnrollScreenEvent
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentScreenState
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentState
import com.example.figerprintconsole.app.utils.AppConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnrollmentViewModel @Inject constructor(
    val enrollmentRepositoryImpl: EnrollmentRepositoryImpl
): ViewModel() {

    // Ensure ONE SOCKET!
    private var socketJob: Job? = null

    // Current UI State
    private val _currentUiState = MutableStateFlow<EnrollmentScreenState>(EnrollmentScreenState.IDLE)
    val currentUiState: StateFlow<EnrollmentScreenState> = _currentUiState

    // Current State Data
    private val _currentStateData = MutableStateFlow(EnrollmentState())
    val currentStateData: StateFlow<EnrollmentState> = _currentStateData

    // Current Input Field State
    private val _currentInputFieldState = MutableStateFlow(NewEnrollUser()) // Why I can't hold data when the screen navigation changes?? Figure me out!
    val currentInputFieldState: StateFlow<NewEnrollUser> = _currentInputFieldState

    // The state will help use in tracking events - and handle viewModel!
    private val _activeWebsocketState = MutableStateFlow<EnrollmentSocketEvent>(EnrollmentSocketEvent.IDLE)

    init { observeWebSocketState() }

    fun onEvent(event: EnrollScreenEvent) {
        when(event) {
            is EnrollScreenEvent.LevelUp -> { levelUpToNextStateEvent() }
            is EnrollScreenEvent.ConnectToSocket -> { connectToSocket() }
            is EnrollScreenEvent.IDLE -> { }

            is EnrollScreenEvent.CANCEL -> { }
            is EnrollScreenEvent.Error -> { }

            is EnrollScreenEvent.TextFieldInput -> { trackInputField(event.newEnrollUser) }
            is EnrollScreenEvent.ResetTextFieldInput -> { resetTextInputField() } // Do use me when necessary!

            else -> { }
        }
    }

    fun connectToSocket() {
        AppConstant.debugMessage("WS: Connecting to Socket observer!", "WS")
        if (socketJob != null) return

        socketJob = viewModelScope.launch {
            enrollmentRepositoryImpl.observeEnrollment()
                .catch { e ->
                    AppConstant.debugMessage("WS ERROR: ${e.message}", "WS")
                }
                .collect { event ->
                    AppConstant.debugMessage("WS: ARRIVED ONE MESSAGE!", "WS")
                    AppConstant.debugMessage("WS: $event", "WS")
                    _activeWebsocketState.value = event
            }
        }
    }

    fun levelUpToNextStateEvent() {
        // Check Current UiState
        when (_currentUiState.value) {
            EnrollmentScreenState.IDLE -> { }
            EnrollmentScreenState.UserInput -> {
                viewModelScope.launch {
                    AppConstant.debugMessage("WS: Start Enrolling", "WS")
                    enrollmentRepositoryImpl.startEnrollment(newEnrollUser = _currentInputFieldState.value )
                }
            }
            EnrollmentScreenState.EnrollingStepOne -> { levelUpToEnrollmentStepTwo() }
            EnrollmentScreenState.EnrollingStepTwo -> {  }
            EnrollmentScreenState.Verifying -> {  }
            EnrollmentScreenState.Completed -> { }
            is EnrollmentScreenState.Error -> { }
        }
    }

    fun levelUpToUserInput() {

        // Change the UiState
        _currentUiState.value = EnrollmentScreenState.UserInput

        // Change the state Data
        _currentStateData.value = _currentStateData.value.copy(
            currentStep = 1,
            isEnrolling = true,
            enrollmentProgress = .25f,
            enrollmentMessage = "",
            isCompleted = false,
            errorMessage = null,
        )
    }
    fun levelUpToEnrollmentStepOne() {

        // Change the UiState
        _currentUiState.value = EnrollmentScreenState.EnrollingStepOne

        // Change the state Data
        _currentStateData.value = _currentStateData.value.copy(
            currentStep = 2, // This should be coming from repo! / server and not vm!
            isEnrolling = true,
            enrollmentProgress = .50f,
            enrollmentMessage = "",
            isCompleted = false,
            errorMessage = null,
        )
    }
    fun levelUpToEnrollmentStepTwo() {
        // Change the UiState
        _currentUiState.value = EnrollmentScreenState.EnrollingStepTwo

        // Change the state Data
        _currentStateData.value = _currentStateData.value.copy(
            currentStep = 3,
            isEnrolling = true,
            enrollmentProgress = .75f,
            enrollmentMessage = "",
            isCompleted = false,
            errorMessage = null,
        )
    }
    fun levelUpToVerifying() {
        // Change the UiState
        _currentUiState.value = EnrollmentScreenState.Verifying

        // Change the state Data
        _currentStateData.value = _currentStateData.value.copy(
            currentStep = 4,
            isEnrolling = true,
            enrollmentProgress = .90f,
            enrollmentMessage = "",
            isCompleted = false,
            errorMessage = null,
        )
    }
    fun levelUpToComplete() {
        // Change the UiState
        _currentUiState.value = EnrollmentScreenState.Completed

        // Change the state Data
        _currentStateData.value = _currentStateData.value.copy(
            currentStep = 4,
            isEnrolling = false,
            enrollmentProgress = 1f,
            enrollmentMessage = "",
            isCompleted = true,
            errorMessage = null,
        )
    }
    fun showError() {
        _currentUiState.value = EnrollmentScreenState.Error(message = "Failed To Connect")
        _currentStateData.value = EnrollmentState(errorMessage = "Failed To Connect")
    }

    fun trackInputField(newEnrollUser: NewEnrollUser) { _currentInputFieldState.value = newEnrollUser }

    fun resetTextInputField() { _currentInputFieldState.value = NewEnrollUser() }

    private fun observeWebSocketState() {
        viewModelScope.launch {
            _activeWebsocketState.collect { event ->
                when (event) {
                    is EnrollmentSocketEvent.IDLE -> { }

                    is EnrollmentSocketEvent.Connected -> { levelUpToUserInput() }
                    is EnrollmentSocketEvent.EnrollPending -> { levelUpToEnrollmentStepOne() }
                    is EnrollmentSocketEvent.EnrollSuccess -> { levelUpToVerifying() }
                    is EnrollmentSocketEvent.AttendanceEvent -> { levelUpToComplete() }

                    is EnrollmentSocketEvent.Disconnected -> { showError() }
                    is EnrollmentSocketEvent.VerificationFailed -> { showError() }
                    is EnrollmentSocketEvent.Error -> { showError() }
                }
            }
        }
    }
}