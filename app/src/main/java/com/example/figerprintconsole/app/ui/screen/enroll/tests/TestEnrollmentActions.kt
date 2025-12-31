package com.example.figerprintconsole.app.ui.screen.enroll.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.figerprintconsole.app.ui.screen.enroll.components.EnrollmentActions
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentScreenState
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentState

@Preview(showBackground = true)
@Composable
fun TestEnrollmentActions() {
    val state = EnrollmentState()
    val uiState = EnrollmentScreenState.IDLE
    EnrollmentActions(uiState, state, {}, {}, {}, {})
}