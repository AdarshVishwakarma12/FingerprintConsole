package com.example.figerprintconsole.app.ui.enroll.tests

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.figerprintconsole.app.ui.screen.enroll.components.FingerprintEnrollmentScreen
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentState
import kotlinx.coroutines.delay

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TestFingerprintEnrollmentScreen() {
    MaterialTheme {
        var state by remember { mutableStateOf(EnrollmentState()) }

        LaunchedEffect(key1 = state.isEnrolling) {
            if (state.isEnrolling && state.enrollmentProgress < 1f) {
                repeat(4) { step ->
                    state = state.copy(
                        enrollmentMessage = "Scanning... ${(step + 1) * 25}%",
                        currentStep = step + 1,
                        enrollmentProgress = (step + 1) * 0.25f
                    )
                    delay(4000)
                }
                state = state.copy(
                    isCompleted = true,
                    enrollmentProgress = 1f,
                    enrollmentMessage = "Enrollment successful!"
                )
            }
        }

        FingerprintEnrollmentScreen(
            state = state,
            onStartEnrollment = {
                state = state.copy(isEnrolling = true)
            },
            onRetry = {
                state = EnrollmentState()
            },
            onComplete = {
                // Handle completion
            }
        )
    }
}