package com.example.figerprintconsole.app.ui.enroll

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.figerprintconsole.app.ui.enroll.components.EnrollmentActions
import com.example.figerprintconsole.app.ui.enroll.components.EnrollmentHeader
import com.example.figerprintconsole.app.ui.enroll.components.EnrollmentProgressSection
import com.example.figerprintconsole.app.ui.enroll.components.ErrorMessage
import com.example.figerprintconsole.app.ui.enroll.components.FingerprintVisualization
import com.example.figerprintconsole.app.ui.enroll.state.EnrollmentState

@Composable
fun FingerprintEnrollmentScreen(
    state: EnrollmentState,
    onStartEnrollment: () -> Unit,
    onRetry: () -> Unit,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title section
            EnrollmentHeader(state)

            Spacer(modifier = Modifier.height(48.dp))

            // Main fingerprint visualization
            FingerprintVisualization(
                state = state,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Progress and instruction
            EnrollmentProgressSection(state)

            Spacer(modifier = Modifier.height(32.dp))

            // Action buttons
            EnrollmentActions(
                state = state,
                onStartEnrollment = onStartEnrollment,
                onRetry = onRetry,
                onComplete = onComplete
            )

            // Error message if any
            state.errorMessage?.let {
                Spacer(modifier = Modifier.height(16.dp))
                ErrorMessage(it)
            }
        }
    }
}