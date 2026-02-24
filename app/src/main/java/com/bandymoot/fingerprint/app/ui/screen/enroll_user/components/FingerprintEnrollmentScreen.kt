package com.bandymoot.fingerprint.app.ui.screen.enroll_user.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser
import com.bandymoot.fingerprint.app.ui.screen.devices.components.ErrorEnrollmentAlertDialog
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentScreenState
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentState

@Composable
fun FingerprintEnrollmentScreen(
    uiState: EnrollmentState,
    onRetry: () -> Unit,
    onStartEnrollment: () -> Unit,
    onInputChanged: (NewEnrollUser) -> Unit,
    onValidateInputAndStartEnroll: () -> Unit,
    onCompleteEnrollment: () -> Unit,
) {

    // val focusManager = LocalFocusManager.current

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Persistent Progress Section
            // EnrollmentProgressSection(uiState)

            // Dynamic Content Area
            Box(
                modifier = Modifier
                    .weight(1f),
                    //.clickable { focusManager.clearFocus(true) },
                contentAlignment = Alignment.Center,
            ) {
                when (uiState.enrollmentScreenState) {
                    is EnrollmentScreenState.IDLE, EnrollmentScreenState.ConnectingToSocket -> {
                        IdleEnrollmentView(
                            uiState = uiState,
                            onStartEnrollment = onStartEnrollment
                        )
                    }
                    is EnrollmentScreenState.UserInfoInput, EnrollmentScreenState.WaitForDevice, EnrollmentScreenState.EnrollmentStarted -> {
                        EnterUserDetail(
                            uiState,
                            onRetry = onRetry,
                            newUser = uiState.userEnrollInfo,
                            onComplete = onValidateInputAndStartEnroll,
                            onInputChanged = { current -> onInputChanged(current) }
                        )
                    }

                    is EnrollmentScreenState.CheckDuplicateFinger, EnrollmentScreenState.FirstScan, EnrollmentScreenState.SecondScan -> {
                        EnrollmentScanningView(state = uiState)
                    }

                    is EnrollmentScreenState.Enrolled -> EnrollmentSuccessView(onComplete = onCompleteEnrollment)
                }
            }

            // Global Error Footer
            uiState.enrollmentErrorState?.let { error -> ErrorEnrollmentAlertDialog(error, onRetry = onRetry) }
        }
    }
}