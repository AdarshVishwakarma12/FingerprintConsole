package com.bandymoot.fingerprint.app.ui.screen.enroll

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.bandymoot.fingerprint.app.ui.screen.enroll.components.FingerprintEnrollmentScreen
import com.bandymoot.fingerprint.app.ui.screen.enroll.event.EnrollScreenEvent
import com.bandymoot.fingerprint.app.ui.screen.socket.SocketEventViewModel
import com.bandymoot.fingerprint.app.utils.AppConstant

@Composable
fun StartEnrollmentProcess(
    onCompleteEnrollment: () -> Unit,
    navBackStackEntry: NavBackStackEntry?,
    socketViewModel: SocketEventViewModel = hiltViewModel()
) {
    val viewModel: EnrollmentViewModel = if(navBackStackEntry==null) { hiltViewModel() } else { hiltViewModel(navBackStackEntry) }
    val uiState by viewModel.uiState.collectAsState()


    val socket = socketViewModel.events.collectAsState()

    AppConstant.debugMessage("CHECK THE DATA OVER HERE :: ${socket.value.data}")

    FingerprintEnrollmentScreen(
        uiState = uiState,
        onStartEnrollment = { viewModel.onEvent(EnrollScreenEvent.StartEnrollment) },
        onRetry = { viewModel.onEvent(EnrollScreenEvent.RESET) },
        onValidateInputAndStartEnroll = { viewModel.onEvent(EnrollScreenEvent.ValidateUserInfoAndStartBiometric) },
        onComplete = { },
        onCompleteEnrollment = onCompleteEnrollment,
        onInputChanged = { it -> viewModel.onEvent(EnrollScreenEvent.TextFieldInput(it)) }
    )
}