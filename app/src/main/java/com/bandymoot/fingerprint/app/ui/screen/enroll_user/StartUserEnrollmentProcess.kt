package com.bandymoot.fingerprint.app.ui.screen.enroll_user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.components.FingerprintEnrollmentScreen
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.event.UserEnrollScreenEvent
import com.bandymoot.fingerprint.app.ui.screen.socket.SocketEventViewModel

@Composable
fun StartEnrollmentProcess(
    onCompleteEnrollment: () -> Unit,
    navBackStackEntry: NavBackStackEntry?,
    socketViewModel: SocketEventViewModel = hiltViewModel()
) {
    val viewModel: UserEnrollmentViewModel = if(navBackStackEntry==null) { hiltViewModel() } else { hiltViewModel(navBackStackEntry) }
    val uiState by viewModel.uiState.collectAsState()

    FingerprintEnrollmentScreen(
        uiState = uiState,
        onStartEnrollment = { viewModel.onEvent(UserEnrollScreenEvent.StartEnrollment) },
        onRetry = { viewModel.onEvent(UserEnrollScreenEvent.RESET) },
        onValidateInputAndStartEnroll = { viewModel.onEvent(UserEnrollScreenEvent.ValidateUserInfoAndStartBiometric) },
        onComplete = { },
        onCompleteEnrollment = onCompleteEnrollment,
        onInputChanged = { it -> viewModel.onEvent(UserEnrollScreenEvent.TextFieldInput(it)) }
    )
}