package com.bandymoot.fingerprint.app.ui.enroll.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bandymoot.fingerprint.app.ui.screen.enroll.components.EnrollmentHeader
import com.bandymoot.fingerprint.app.ui.screen.enroll.state.EnrollmentScreenState
import com.bandymoot.fingerprint.app.ui.screen.enroll.state.EnrollmentState

@Preview(showBackground = true)
@Composable
fun TestEnrollmentHeader() {
    val uiState = EnrollmentScreenState.IDLE
    val state = EnrollmentState()

    EnrollmentHeader(uiState, state)
}