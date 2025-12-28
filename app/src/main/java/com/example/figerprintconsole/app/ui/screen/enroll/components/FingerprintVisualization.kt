package com.example.figerprintconsole.app.ui.enroll.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.figerprintconsole.app.ui.enroll.state.EnrollmentState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FingerprintVisualization(
    state: EnrollmentState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Background fingerprint pattern
        // Change this!! to a vector with animated color!!
        FingerprintPattern(
            isAnimating = state.isEnrolling,
            progress = state.enrollmentProgress
        )

        // Progress ring
        CircularProgressIndicator(
            progress = { state.enrollmentProgress },
            modifier = Modifier.fillMaxSize(),
            strokeWidth = 4.dp,
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primary
        )

        // Center icon or checkmark
        AnimatedContent(
            targetState = state.isCompleted,
            transitionSpec = {
                scaleIn() + fadeIn() with scaleOut() + fadeOut()
            }
        ) { completed ->
            if(completed) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ok",
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Text(
                    text = "${state.currentStep}/${state.totalSteps}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}