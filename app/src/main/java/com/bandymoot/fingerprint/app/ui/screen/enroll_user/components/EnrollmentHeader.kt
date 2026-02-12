package com.bandymoot.fingerprint.app.ui.screen.enroll_user.components

import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentState

@Composable
fun EnrollmentHeader(
    uiState: EnrollmentState,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val text = "Fingerprint Enrollment"

        // Show Header
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

    }
}