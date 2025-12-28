package com.example.figerprintconsole.app.ui.enroll.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.figerprintconsole.app.ui.enroll.components.StepIndicators

@Preview(showBackground = true)
@Composable
fun TestStepIndicator() {
    val currentStep = 2
    val totalSteps = 4
    val isCompleted = false

    StepIndicators(currentStep, totalSteps, isCompleted)
}