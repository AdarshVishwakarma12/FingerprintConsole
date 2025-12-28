package com.example.figerprintconsole.app.ui.enroll.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.figerprintconsole.app.ui.enroll.components.EnrollmentProgressSection
import com.example.figerprintconsole.app.ui.enroll.state.EnrollmentState

@Preview(showBackground = true)
@Composable
fun TestEnrollmentProgressSection() {
    val state = EnrollmentState()
    EnrollmentProgressSection(state)
}