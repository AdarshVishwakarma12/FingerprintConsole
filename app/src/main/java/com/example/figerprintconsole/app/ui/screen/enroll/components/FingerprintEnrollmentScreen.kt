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
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentState

@Composable
fun FingerprintEnrollmentScreen(
    state: EnrollmentState,
    onStartEnrollment: () -> Unit,
    onRetry: () -> Unit,
    onComplete: () -> Unit,
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title section
            EnrollmentHeader(state)

            Spacer(modifier = Modifier.height(48.dp))

            // Progress and instruction
            EnrollmentProgressSection(state)

            Spacer(modifier = Modifier.height(32.dp))

            // Enrollment actions -> handles start and stop
            // EnrollmentActionOne -> handles

            when {
                (!state.isEnrolling) -> {
                    // This is most likely to call onStartEnrollment
                    EnrollmentActions(
                        state = state,
                        onStartEnrollment = onStartEnrollment,
                        onRetry = onRetry,
                        onComplete = onComplete
                    )
                }

                (state.currentStep == 1) -> {
                    EnrollmentActionOne(
                        state = state,
                        onRetry = onRetry,
                        onComplete = onComplete,
                        newUser = NewEnrollUser(),
                        onInputChanged = {}
                    )
                }

                (state.currentStep == 2 || state.currentStep == 3) -> {
                    EnrollmentActionTwo(
                        state = state,
                        onRetry = onRetry,
                        onComplete = onComplete
                    )
                }

                (state.currentStep == 4) -> {
                    EnrollmentActions(
                        state = state,
                        onStartEnrollment = onStartEnrollment,
                        onRetry = onRetry,
                        onComplete = onComplete
                    )
                }
            }

            // Error message if any
            state.errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                ErrorMessage(it)
            }
        }
    }
}