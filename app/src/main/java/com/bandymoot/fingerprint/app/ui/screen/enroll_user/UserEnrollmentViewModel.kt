package com.bandymoot.fingerprint.app.ui.screen.enroll_user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.data.socket.SocketEnrollmentStep
import com.bandymoot.fingerprint.app.data.socket.SocketEvent
import com.bandymoot.fingerprint.app.data.socket.SocketManager
import com.bandymoot.fingerprint.app.data.socket.SocketTopicEnroll
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser
import com.bandymoot.fingerprint.app.domain.repository.DeviceRepository
import com.bandymoot.fingerprint.app.domain.usecase.EnrollUserUseCase
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.event.UserEnrollScreenEvent
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentErrorState
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentScreenState
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentSocketState
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
    private val enrollUserUseCase: EnrollUserUseCase,
    private val deviceRepository: DeviceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EnrollmentState())
    val uiState: StateFlow<EnrollmentState> = _uiState

    private var enrollmentTimeoutJob: Job? = null

    // List Every Device the Manager Has Access to && Add Observer to the Screen!
    init {
        viewModelScope.launch {
            val deviceResult = deviceRepository.observeDeviceByCurrentManager()
            deviceResult.collect { result ->
                if(result is RepositoryResult.Success) _uiState.update { it.copy(listOfDevice = result.data) }
            }
        }
        observeWebSocketState()
    }

    // UI EVENTS
    fun onEvent(event: UserEnrollScreenEvent) {
        when (event) {
            is UserEnrollScreenEvent.StartEnrollment -> moveTo(EnrollmentScreenState.ConnectingToSocket)
            is UserEnrollScreenEvent.TextFieldInput -> _uiState.update { it.copy(userEnrollInfo = event.newEnrollUser ) }
            is UserEnrollScreenEvent.ResetTextFieldInput -> _uiState.update { it.copy(userEnrollInfo = NewEnrollUser()) }
            is UserEnrollScreenEvent.ValidateUserInfoAndStartBiometric -> startEnrollment()
            // FOR UserEnrollScreenEvent.RESET :: Don't update the userEnrollInfo / it will reset user info!
            is UserEnrollScreenEvent.RESET -> _uiState.update { it.copy(isCompleted = false, enrollmentScreenState = EnrollmentScreenState.IDLE, isLoading = false) }
            is UserEnrollScreenEvent.DismissError -> _uiState.update { it.copy(enrollmentErrorState = null) }
            else -> Unit
        }
    }

    // ENROLL FLOW -> Hit the REST Api
    private fun startEnrollment() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val data = enrollUserUseCase(_uiState.value.userEnrollInfo)) {
                is RepositoryResult.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    moveTo(EnrollmentScreenState.WaitForDevice)
                }
                is RepositoryResult.Failed -> {
                    _uiState.update { it.copy(isLoading = false) }
                    fallbackTo(data.throwable.message ?: data.throwable.localizedMessage)
                }
            }
        }
    }

    // SOCKET EVENTS -> Ears of screen
    private fun observeWebSocketState() {
        viewModelScope.launch {
            SocketManager.socketEvent.collect { event ->
                when (val current = event) {
                    is SocketEvent.EnrollProgress -> handleEnrollSteps(current.data)
                    is SocketEvent.Error -> fallbackTo(event.message)
                    is SocketEvent.Disconnected -> createConnectionWithSocket() // Even for un-expected Disconnect, retry again.
                    else -> Unit
                }
            }
        }
    }

    private fun handleEnrollSteps(data: SocketTopicEnroll) {
        when(data.step) {
            is SocketEnrollmentStep.Start -> moveTo(EnrollmentScreenState.EnrollmentStarted)
            is SocketEnrollmentStep.CheckDuplicateFinger -> moveTo(EnrollmentScreenState.CheckDuplicateFinger)
            is SocketEnrollmentStep.FirstScan -> moveTo(EnrollmentScreenState.FirstScan)
            is SocketEnrollmentStep.SecondScan -> moveTo(EnrollmentScreenState.SecondScan)
            is SocketEnrollmentStep.Success -> moveTo(EnrollmentScreenState.Enrolled)
            is SocketEnrollmentStep.Failed -> fallbackTo(data.message)
            is SocketEnrollmentStep.UndefinedStep -> Unit
        }
    }

    // STATE MACHINE (CORE)
    private fun moveTo(next: EnrollmentScreenState) {
        _uiState.update { current ->

            val currentState = current.enrollmentScreenState

            val allowed = when (currentState) {
                EnrollmentScreenState.IDLE -> next is EnrollmentScreenState.ConnectingToSocket
                EnrollmentScreenState.ConnectingToSocket -> next is EnrollmentScreenState.UserInfoInput
                EnrollmentScreenState.UserInfoInput -> next is EnrollmentScreenState.WaitForDevice
                EnrollmentScreenState.WaitForDevice -> next is EnrollmentScreenState.EnrollmentStarted
                EnrollmentScreenState.EnrollmentStarted -> next is EnrollmentScreenState.CheckDuplicateFinger
                EnrollmentScreenState.CheckDuplicateFinger -> next is EnrollmentScreenState.FirstScan
                EnrollmentScreenState.FirstScan -> next is EnrollmentScreenState.SecondScan
                EnrollmentScreenState.SecondScan -> next is EnrollmentScreenState.Enrolled
                EnrollmentScreenState.Enrolled -> false
            }

            if (!allowed) { return@update current }
            current.copy(enrollmentScreenState = next, errorMessage = null)
        }
        startOrCancelEnrollmentTimeout(next)
    }

    private fun fallbackTo(error: String) {
        _uiState.update { current ->

            val currentState = current.enrollmentScreenState

            val fallbackState: EnrollmentScreenState = when(currentState) {
                is EnrollmentScreenState.IDLE,
                is EnrollmentScreenState.ConnectingToSocket -> {
                    EnrollmentScreenState.IDLE
                }
                is EnrollmentScreenState.UserInfoInput,
                is EnrollmentScreenState.WaitForDevice,
                is EnrollmentScreenState.EnrollmentStarted,
                is EnrollmentScreenState.CheckDuplicateFinger,
                is EnrollmentScreenState.FirstScan,
                is EnrollmentScreenState.SecondScan -> {
                    EnrollmentScreenState.UserInfoInput
                }
                is EnrollmentScreenState.Enrolled -> {
                    EnrollmentScreenState.Enrolled
                }
            }

            current.copy(enrollmentScreenState = fallbackState, enrollmentErrorState = EnrollmentErrorState(error))
        }
    }

    private fun startOrCancelEnrollmentTimeout(step: EnrollmentScreenState) {
        when(step) {
            is EnrollmentScreenState.ConnectingToSocket -> { createConnectionWithSocket() }
            is EnrollmentScreenState.WaitForDevice -> {
                enrollmentTimeoutJob?.cancel()
                enrollmentTimeoutJob = viewModelScope.launch {
                    delay(AppConstant.ENROLLMENT_TIMEOUT)
                    fallbackTo("Enrollment timed out. Please try again.")
                }
            }
            is EnrollmentScreenState.Enrolled -> { enrollmentTimeoutJob?.cancel() }
            else -> Unit
        }
    }

    private fun createConnectionWithSocket() {
        // build the exponential-backoff tries to connect to Socket! - for weak connections!
        val connectedWithSocket = SocketManager.hasActiveConnection()
        if(connectedWithSocket) {
            _uiState.update { it.copy(enrollmentSocketState = EnrollmentSocketState.CONNECTED) }
            viewModelScope.launch { delay(3000); moveTo(EnrollmentScreenState.UserInfoInput) }
        } else {
            _uiState.update { it.copy(enrollmentSocketState = EnrollmentSocketState.DISCONNECTED) }
            fallbackTo("Failed to build connection with Socket")
        }
    }
}