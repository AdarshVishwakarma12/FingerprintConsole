package com.bandymoot.fingerprint.app.ui.screen.enroll

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.bandymoot.fingerprint.app.ui.screen.enroll.components.FingerprintEnrollmentScreen
import com.bandymoot.fingerprint.app.ui.screen.enroll.event.EnrollScreenEvent

@Composable
fun StartEnrollmentProcess(
    onCompleteEnrollment: () -> Unit,
    navBackStackEntry: NavBackStackEntry?
) {
    val viewModel: EnrollmentViewModel = if(navBackStackEntry==null) { hiltViewModel() } else { hiltViewModel(navBackStackEntry) }

    // These two values changes together -> cause 2 recomposition -> Fix in later version!
    val currentUiState by viewModel.currentUiState.collectAsState()
    val currentStateData by viewModel.currentStateData.collectAsState()
    val currentTextFieldData by viewModel.currentInputFieldState.collectAsState()

    FingerprintEnrollmentScreen(
        uiState = currentUiState,
        stateData = currentStateData,
        currentTextFieldData = currentTextFieldData,
        onStartEnrollment = { viewModel.onEvent(EnrollScreenEvent.ConnectToSocket) },
        onRetry = { viewModel.onEvent(EnrollScreenEvent.IDLE) },
        onComplete = {
            // Level 2 & 3 are only updated within viewmodel, currently the business logic is inside UI!!
            // Fix as soon / or after connecting the Websocket!!
            viewModel.onEvent(EnrollScreenEvent.LevelUp) },
        onCompleteEnrollment = onCompleteEnrollment,
        onInputChanged = { it -> viewModel.onEvent(EnrollScreenEvent.TextFieldInput(it)) }
    )
}