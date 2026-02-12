package com.bandymoot.fingerprint.app.ui.screen.enroll_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.data.socket.SocketEvent
import com.bandymoot.fingerprint.app.data.socket.SocketManager
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser
import com.bandymoot.fingerprint.app.domain.usecase.EnrollUserUseCase
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.event.UserEnrollScreenEvent
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentScreenState
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentState
import com.bandymoot.fingerprint.app.utils.AppConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserEnrollmentViewModel @Inject constructor(
    private val enrollUserUseCase: EnrollUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EnrollmentState())
    val uiState: StateFlow<EnrollmentState> = _uiState

    private val _socketEvents = MutableStateFlow<SocketEvent>(SocketEvent.IDLE)

    private var stepJob: Job? = null
    private var enrollmentTimeoutJob: Job? = null

    init {
        SocketManager.setListener { event ->
            viewModelScope.launch {
                _socketEvents.emit(event)
            }
        }
        observeWebSocketState()
    }

    // UI EVENTS
    fun onEvent(event: UserEnrollScreenEvent) {
        when (event) {

            UserEnrollScreenEvent.StartEnrollment -> {
                if(SocketManager.hasActiveConnection()) {
                    moveTo(EnrollmentScreenState.UserInput)
                }
                else {
                    moveTo(EnrollmentScreenState.Error("Connection failed"))
                }
            }

            UserEnrollScreenEvent.ValidateUserInfoAndStartBiometric -> {
                startEnrollment()
            }

            is UserEnrollScreenEvent.TextFieldInput -> {
                _uiState.update { it.copy(userEnrollInfo = event.newEnrollUser ) }
            }

            UserEnrollScreenEvent.ResetTextFieldInput -> {
                _uiState.update {
                    it.copy(userEnrollInfo = NewEnrollUser())
                }
            }

            UserEnrollScreenEvent.RESET -> { _uiState.update {
                it.copy(
                    isCompleted = false,
                    userEnrollInfo = NewEnrollUser(),
                    enrollmentScreenState = EnrollmentScreenState.IDLE,
                    isLoading = false
                )
            } }

            else -> Unit
        }
    }

    // ENROLL FLOW
    private fun startEnrollment() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            when (val data = enrollUserUseCase(_uiState.value.userEnrollInfo)) {

                is RepositoryResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    moveTo(EnrollmentScreenState.EnrollingStepOne)
                    delayAndAdvance()
                }

                is RepositoryResult.Failed -> {
                    _uiState.update { it.copy(isLoading = false) }
                    moveTo(EnrollmentScreenState.Error(data.throwable.message))
                }
            }
        }
    }

    // STEP TIMING
    private fun delayAndAdvance() {
        stepJob?.cancel()
        stepJob = viewModelScope.launch {
            delay(3_000)

            if (_uiState.value.enrollmentScreenState ==
                EnrollmentScreenState.EnrollingStepOne
            ) {
                moveTo(EnrollmentScreenState.EnrollingStepTwo)
            }
        }
    }

    private fun startEnrollmentTimeout() {
        enrollmentTimeoutJob?.cancel()

        enrollmentTimeoutJob = viewModelScope.launch {
            delay(AppConstant.ENROLLMENT_TIMEOUT)

            if (_uiState.value.enrollmentScreenState ==
                EnrollmentScreenState.EnrollingStepTwo
            ) {
                AppConstant.debugMessage(
                    "Enrollment timed out",
                    "ENROLL"
                )
                moveTo(
                    EnrollmentScreenState.Error("Enrollment timed out. Please try again.")
                )
            }
        }
    }

    private fun onEnteredState(state: EnrollmentScreenState) {
        when (state) {

            EnrollmentScreenState.EnrollingStepTwo -> {
                startEnrollmentTimeout()
            }

            EnrollmentScreenState.Enrolled,
            is EnrollmentScreenState.Error,
            EnrollmentScreenState.Completed -> {
                enrollmentTimeoutJob?.cancel()
            }

            else -> Unit
        }
    }


    // SOCKET EVENTS
    private fun observeWebSocketState() {
        viewModelScope.launch {
            _socketEvents.collect { event ->
                when (event) {
                    is SocketEvent.Attendance -> handleAttendance(event)

                    is SocketEvent.Error -> {
                        moveTo(EnrollmentScreenState.Error("Socket error"))
                    }

                    is SocketEvent.Disconnected -> {
                        moveTo(EnrollmentScreenState.Error("Disconnected from socket"))
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun handleAttendance(event: SocketEvent.Attendance) {
        val state = _uiState.value

        val validStep =
            state.enrollmentScreenState is EnrollmentScreenState.EnrollingStepOne ||
                    state.enrollmentScreenState is EnrollmentScreenState.EnrollingStepTwo

        if (!validStep) {
            AppConstant.debugMessage(
                "Attendance ignored — invalid state: ${state.enrollmentScreenState}",
                "ENROLL"
            )
            return
        }

        if (state.userEnrollInfo.name == event.data.name) {
            stepJob?.cancel()
            enrollmentTimeoutJob?.cancel()
            moveTo(EnrollmentScreenState.Enrolled)
        } else {
            moveTo(EnrollmentScreenState.Error("Different user enrolled"))
        }
    }

    // STATE MACHINE (CORE)
    private fun moveTo(next: EnrollmentScreenState) {
        _uiState.update { current ->

            val currentState = current.enrollmentScreenState

            val allowed = when (currentState) {
                EnrollmentScreenState.IDLE ->
                    next is EnrollmentScreenState.UserInput || next is EnrollmentScreenState.Error

                EnrollmentScreenState.UserInput ->
                        next is EnrollmentScreenState.EnrollingStepOne || next is EnrollmentScreenState.Error

                EnrollmentScreenState.EnrollingStepOne ->
                    next is EnrollmentScreenState.EnrollingStepTwo

                EnrollmentScreenState.EnrollingStepTwo ->
                    next is EnrollmentScreenState.Enrolled || next is EnrollmentScreenState.Error

                EnrollmentScreenState.Enrolled ->
                    next is EnrollmentScreenState.Verifying || next is EnrollmentScreenState.Error

                EnrollmentScreenState.Verifying ->
                    next is EnrollmentScreenState.Completed

                EnrollmentScreenState.Completed ->
                    false

                is EnrollmentScreenState.Error ->
                    next is EnrollmentScreenState.IDLE
            }

            if (!allowed) {
                AppConstant.debugMessage(
                    "Invalid transition: $currentState → $next",
                    "ENROLL"
                )
                return@update current
            }

            AppConstant.debugMessage(
                "Transition: $currentState → $next",
                "ENROLL"
            )

            current.copy(
                enrollmentScreenState = next,
                errorMessage = null
            )
        }

        onEnteredState(next)
    }

    override fun onCleared() {
        super.onCleared()
        stepJob?.cancel()
        enrollmentTimeoutJob?.cancel()
        // SocketManager.clearListener()
    }
}