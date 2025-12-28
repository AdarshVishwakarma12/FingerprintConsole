package com.example.figerprintconsole.app.ui.enroll.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.figerprintconsole.app.ui.enroll.components.FingerprintVisualization
import com.example.figerprintconsole.app.ui.enroll.state.EnrollmentState

@Preview(showBackground = true)
@Composable
fun TestFingerprintVisualization() {
    val state = EnrollmentState(
        isEnrolling = true,
        isCompleted = false
    )
    FingerprintVisualization(state)
}