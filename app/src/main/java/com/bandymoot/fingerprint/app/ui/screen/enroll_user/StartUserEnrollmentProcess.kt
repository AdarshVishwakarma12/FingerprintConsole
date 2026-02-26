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
    onDismiss: () -> Unit
) {
    val viewModel: UserEnrollmentViewModel = if(navBackStackEntry==null) { hiltViewModel() } else { hiltViewModel(navBackStackEntry) }
    val uiState by viewModel.uiState.collectAsState()

    FingerprintEnrollmentScreen(
        uiState = uiState,
        onRetry = { viewModel.onEvent(UserEnrollScreenEvent.DismissError) },
        onStartEnrollment = { viewModel.onEvent(UserEnrollScreenEvent.StartEnrollment) },
        onInputChanged = { it -> viewModel.onEvent(UserEnrollScreenEvent.TextFieldInput(it)) },
        onValidateInputAndStartEnroll = { viewModel.onEvent(UserEnrollScreenEvent.ValidateUserInfoAndStartBiometric) },
        onCompleteEnrollment = onCompleteEnrollment,
        onDismiss = onDismiss
    )
}