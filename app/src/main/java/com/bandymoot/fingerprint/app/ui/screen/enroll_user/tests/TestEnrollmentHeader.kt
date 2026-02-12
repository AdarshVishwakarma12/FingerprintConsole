package com.bandymoot.fingerprint.app.ui.screen.enroll_user.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.components.EnrollmentHeader
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentState

@Preview(showBackground = true)
@Composable
fun TestEnrollmentHeader() {
    val state = EnrollmentState()

    EnrollmentHeader(state)
}