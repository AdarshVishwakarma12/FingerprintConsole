package com.bandymoot.fingerprint.app.ui.screen.enroll.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser
import com.bandymoot.fingerprint.app.ui.enroll.components.ErrorMessage
import com.bandymoot.fingerprint.app.ui.screen.enroll.state.EnrollmentScreenState
import com.bandymoot.fingerprint.app.ui.screen.enroll.state.EnrollmentState
import com.bandymoot.fingerprint.app.utils.AppConstant
import com.bandymoot.fingerprint.app.utils.DebugType

@Composable
fun FingerprintEnrollmentScreen(
    uiState: EnrollmentState,
    onStartEnrollment: () -> Unit,
    onRetry: () -> Unit,    // Need to build retry mechanism!
    onComplete: () -> Unit,
    onValidateInputAndStartEnroll: () -> Unit,
    onCompleteEnrollment: () -> Unit,
    onInputChanged: (NewEnrollUser) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Title section
            EnrollmentHeader(uiState)

            Spacer(modifier = Modifier.height(48.dp))

            // Progress and instruction
            EnrollmentProgressSection(uiState)

            Spacer(modifier = Modifier.height(32.dp))

            when(uiState.enrollmentScreenState) {
                is EnrollmentScreenState.IDLE -> {
                    // Call onStartEnrollment
                    EnrollmentActions(
                        uiState = uiState,
                        onStartEnrollment = onStartEnrollment,
                        onRetry = onRetry,
                        onComplete = onComplete,
                        onCompleteEnrollment = onCompleteEnrollment
                    )
                }

                is EnrollmentScreenState.UserInput -> {
                    AppConstant.debugMessage("The UserInput is been rendered!", debugType = DebugType.DESCRIPTION)
                    EnrollmentActionOne(
                        uiState = uiState,
                        onRetry = onRetry,
                        onComplete = onValidateInputAndStartEnroll,
                        newUser = uiState.userEnrollInfo,
                        onInputChanged = onInputChanged
                    )
                }

                is EnrollmentScreenState.EnrollingStepOne, EnrollmentScreenState.EnrollingStepTwo -> {
                    EnrollmentActionTwo(
                        uiState = uiState,
                        onRetry = onRetry,
                        onComplete = onComplete
                    )
                }

                is EnrollmentScreenState.Verifying -> {
                    // level up
                    // No Verification for now!
                    onComplete()
                }

                is EnrollmentScreenState.Completed -> {
                    EnrollmentActions(
                        uiState = uiState,
                        onStartEnrollment = onStartEnrollment,
                        onRetry = onRetry,
                        onComplete = onComplete,
                        onCompleteEnrollment = onCompleteEnrollment
                    )
                }
                else -> { }
            }

            // Error message if any
            uiState.errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                ErrorMessage(it)
            }
        }
    }
}