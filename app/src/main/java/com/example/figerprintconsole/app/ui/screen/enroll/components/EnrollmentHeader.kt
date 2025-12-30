package com.example.figerprintconsole.app.ui.screen.enroll.components

import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentState

@Composable
fun EnrollmentHeader(
    state: EnrollmentState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val text = if(state.isCompleted) {
            "Enrollment Complete!"// + state.enrollmentMessage + "Step: ${state.currentStep}"
        } else {
            "Fingerprint Enrollment"// + state.enrollmentMessage + "Step: ${state.currentStep}"
        }

        // Show Header
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

    }
}