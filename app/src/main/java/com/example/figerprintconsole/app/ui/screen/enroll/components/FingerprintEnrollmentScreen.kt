package com.example.figerprintconsole.app.ui.screen.enroll.components

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
import com.example.figerprintconsole.app.domain.model.NewEnrollUser
import com.example.figerprintconsole.app.ui.enroll.components.ErrorMessage
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentScreenState
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentState
import com.example.figerprintconsole.app.utils.AppConstant
import com.example.figerprintconsole.app.utils.DebugType

@Composable
fun FingerprintEnrollmentScreen(
    uiState: EnrollmentScreenState,
    stateData: EnrollmentState,
    currentTextFieldData: NewEnrollUser,
    onStartEnrollment: () -> Unit,
    onRetry: () -> Unit,
    onComplete: () -> Unit,
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
            EnrollmentHeader(uiState, stateData)

            Spacer(modifier = Modifier.height(48.dp))

            // Progress and instruction
            EnrollmentProgressSection(stateData)

            Spacer(modifier = Modifier.height(32.dp))

            // Enrollment actions -> handles start and stop
            // EnrollmentActionOne -> handles

            AppConstant.debugMessage("Current UiState: $uiState && data: $stateData")
            when(uiState) {
                is EnrollmentScreenState.IDLE -> {
                    // Call onStartEnrollment
                    EnrollmentActions(
                        uiState = uiState,
                        state = stateData,
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
                        state = stateData,
                        onRetry = onRetry,
                        onComplete = onComplete,
                        newUser = currentTextFieldData,
                        onInputChanged = onInputChanged
                    )
                }

                is EnrollmentScreenState.EnrollingStepOne, EnrollmentScreenState.EnrollingStepTwo -> {
                    EnrollmentActionTwo(
                        uiState = uiState,
                        state = stateData,
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
                        state = stateData,
                        onStartEnrollment = onStartEnrollment,
                        onRetry = onRetry,
                        onComplete = onComplete,
                        onCompleteEnrollment = onCompleteEnrollment
                    )
                }
                else -> { }
            }

            // Error message if any
            stateData.errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                ErrorMessage(it)
            }
        }
    }
}