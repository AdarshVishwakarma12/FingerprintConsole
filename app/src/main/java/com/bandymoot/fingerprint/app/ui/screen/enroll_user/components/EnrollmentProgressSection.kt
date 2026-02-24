package com.bandymoot.fingerprint.app.ui.screen.enroll_user.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentScreenState
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentState

@Composable
fun EnrollmentProgressSection(
    state: EnrollmentState
) {
    val screenState = state.enrollmentScreenState

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Large Visual Cue
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            // You can replace this Icon with a Lottie Animation for "Scanning"
            Icon(
                imageVector = Icons.Default.Fingerprint,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = if (screenState.isEnrolling) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.outline
            )

            // Circular progress ring around the fingerprint
            CircularProgressIndicator(
                progress = { screenState.enrollmentProgress },
                modifier = Modifier.fillMaxSize(),
                strokeWidth = 4.dp,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Step Bubbles (The "Timeline")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(6) { index ->
                val stepNum = index + 1
                val isCompleted = screenState.currentStep > stepNum
                val isCurrent = screenState.currentStep == stepNum

                StepDot(isCompleted = isCompleted, isCurrent = isCurrent)

                if (index < 5) {
                    HorizontalDivider(
                        modifier = Modifier.width(16.dp),
                        thickness = 2.dp,
                        color = if (isCompleted) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Status Text
        Text(
            text = screenState.enrollmentMessage,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        if (screenState is EnrollmentScreenState.FirstScan || screenState is EnrollmentScreenState.SecondScan) {
            Text(
                text = "Keep your finger still",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun StepDot(isCompleted: Boolean, isCurrent: Boolean) {
    val color = when {
        isCompleted -> MaterialTheme.colorScheme.primary
        isCurrent -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
    val size = if (isCurrent) 12.dp else 8.dp

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}