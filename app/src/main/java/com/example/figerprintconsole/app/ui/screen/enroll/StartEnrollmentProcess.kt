package com.example.figerprintconsole.app.ui.screen.enroll

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.figerprintconsole.app.ui.screen.enroll.components.FingerprintEnrollmentScreen
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentState

@Composable
fun StartEnrollmentProcess(
    onCompleteEnrollment: () -> Unit
) {
    var currentStateStep by remember { mutableIntStateOf(0) }
    var state by remember { mutableStateOf(EnrollmentState()) }

    LaunchedEffect(key1 = currentStateStep) {

        if(currentStateStep == state.totalSteps) {
            state = state.copy(
                isCompleted = true,
                currentStep = currentStateStep,
                enrollmentProgress = 1f,
                enrollmentMessage = "Enrollment successful!"
            )
        } else {
            state = state.copy(
                currentStep = currentStateStep,
                enrollmentProgress = (currentStateStep) * 0.25f,
                enrollmentMessage = "Scanning... ${(currentStateStep) * 25}%"
            )
        }
    }

    FingerprintEnrollmentScreen(
        state = state,
        onStartEnrollment = {
            state = state.copy(isEnrolling = true)
            currentStateStep = (currentStateStep + 1).coerceAtMost(state.totalSteps) // max(a, b)
        },
        onRetry = { state = EnrollmentState() },
        onComplete = {
            if(currentStateStep >= state.totalSteps) onCompleteEnrollment()
            currentStateStep += 1
        }
    )
}