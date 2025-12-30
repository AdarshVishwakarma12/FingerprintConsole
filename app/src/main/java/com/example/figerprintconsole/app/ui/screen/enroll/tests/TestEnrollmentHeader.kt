package com.example.figerprintconsole.app.ui.enroll.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.figerprintconsole.app.ui.screen.enroll.components.EnrollmentHeader
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentState

@Preview(showBackground = true)
@Composable
fun TestEnrollmentHeader() {
    val state = EnrollmentState()

    EnrollmentHeader(state)
}