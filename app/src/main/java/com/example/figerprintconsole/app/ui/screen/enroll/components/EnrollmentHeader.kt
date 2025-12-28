package com.example.figerprintconsole.app.ui.enroll.components

import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import com.example.figerprintconsole.app.ui.enroll.state.EnrollmentState

@Composable
fun EnrollmentHeader(
    state: EnrollmentState
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Show Header
        Text(
            text = if (state.isCompleted) "Enrollment Complete!" else "Fingerprint Enrollment",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

    }
}