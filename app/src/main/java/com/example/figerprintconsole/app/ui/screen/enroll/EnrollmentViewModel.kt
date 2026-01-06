package com.example.figerprintconsole.app.ui.screen.enroll

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.figerprintconsole.app.data.repository.EnrollmentRepositoryImpl
import com.example.figerprintconsole.app.domain.model.NewEnrollUser
import com.example.figerprintconsole.app.ui.screen.enroll.event.EnrollScreenEvent
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentScreenState
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentState
import com.example.figerprintconsole.app.utils.AppConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnrollmentViewModel @Inject constructor(
    val enrollmentRepositoryImpl: EnrollmentRepositoryImpl
): ViewModel() {

    private val _currentUiState = MutableStateFlow<EnrollmentScreenState>(EnrollmentScreenState.IDLE)
    val currentUiState: StateFlow<EnrollmentScreenState> = _currentUiState

    private val _currentStateData = MutableStateFlow(EnrollmentState())
    val currentStateData: StateFlow<EnrollmentState> = _currentStateData

    private val _currentInputFieldState = MutableStateFlow(NewEnrollUser()) // Why I can't hold data when the screen navigation changes?? Figure me out!
    val currentInputFieldState: StateFlow<NewEnrollUser> = _currentInputFieldState

    fun onEvent(event: EnrollScreenEvent) {
        when(event) {
            is EnrollScreenEvent.LevelUp -> { levelUpToNextState() }
            is EnrollScreenEvent.IDLE -> { }
            is EnrollScreenEvent.UserInput -> { levelUpToUserInput() }
            is EnrollScreenEvent.EnrollingStepOne -> { levelUpToEnrollmentStepOne() }
            is EnrollScreenEvent.EnrollingStepTwo -> { levelUpToEnrollmentStepTwo() }
            is EnrollScreenEvent.Verifying -> { levelUpToVerifying() }
            is EnrollScreenEvent.Completed -> { levelUpToComplete() }
            is EnrollScreenEvent.CANCEL -> { }
            is EnrollScreenEvent.Error -> { }

            is EnrollScreenEvent.TextFieldInput -> { trackInputField(event.newEnrollUser) }
            is EnrollScreenEvent.ResetTextFieldInput -> { resetTextInputField() } // Do use me when necessary!
        }
    }

    fun connectToSocket() {
        AppConstant.debugMessage("WS: Connecting to Socket observer!", "WS")
        viewModelScope.launch {
            enrollmentRepositoryImpl.observeEnrollment().collect {
                event ->
                AppConstant.debugMessage("WS: $event", "WS")
            }
        }
    }

    fun levelUpToNextState() {
        // Check Current UiState
        when (_currentUiState.value) {
            EnrollmentScreenState.IDLE -> { levelUpToUserInput() }
            EnrollmentScreenState.UserInput -> { levelUpToEnrollmentStepOne() }
            EnrollmentScreenState.EnrollingStepOne -> { levelUpToEnrollmentStepTwo() }
            EnrollmentScreenState.EnrollingStepTwo -> { levelUpToVerifying() }
            EnrollmentScreenState.Verifying -> { levelUpToComplete() }
            EnrollmentScreenState.Completed -> { }
            is EnrollmentScreenState.Error -> { }
        }
    }

    fun levelUpToUserInput() {

        connectToSocket()

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

        // Send Signal to Repository
        viewModelScope.launch {
            enrollmentRepositoryImpl.startEnrollment(currentInputFieldState.value)
        }

        // Change the UiState
        _currentUiState.value = EnrollmentScreenState.EnrollingStepOne

        // Change the state Data
        _currentStateData.value = _currentStateData.value.copy(
            currentStep = 2,
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

    fun showError() {}

    fun trackInputField(newEnrollUser: NewEnrollUser) {
        _currentInputFieldState.value = newEnrollUser
    }

    fun resetTextInputField() {
        _currentInputFieldState.value = NewEnrollUser()
    }
}