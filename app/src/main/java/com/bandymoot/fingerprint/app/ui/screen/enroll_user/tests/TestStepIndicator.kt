package com.bandymoot.fingerprint.app.ui.screen.enroll_user.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.components.StepIndicators

@Preview(showBackground = true)
@Composable
fun TestStepIndicator() {
    val currentStep = 2
    val totalSteps = 4
    val isCompleted = false

    StepIndicators(currentStep, totalSteps, isCompleted)
}