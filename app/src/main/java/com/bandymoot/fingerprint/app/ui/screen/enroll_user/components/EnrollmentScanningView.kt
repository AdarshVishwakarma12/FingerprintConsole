package com.bandymoot.fingerprint.app.ui.screen.enroll_user.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Fingerprint
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bandymoot.fingerprint.R
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentScreenState
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentState

@Composable
fun EnrollmentScanningView(
    state: EnrollmentState,
) {
    val screenState = state.enrollmentScreenState

    // Animation for the pulsing effect
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 1. Progress Badge (Shows 1/3, 2/3, etc)
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Text(
                text = "STEP ${screenState.currentStep - 2} OF 3", // Mapping steps 3,4,5 to 1,2,3
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // 2. Animated Scanner Visual
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(280.dp)) {
            // Pulse Ring
            if (screenState.isEnrolling) {
                Surface(
                    modifier = Modifier
                        .size(180.dp * pulseScale)
                        .graphicsLayer { alpha = 2f - pulseScale },
                    shape = CircleShape,
                    color = colorResource(R.color.strat_blue).copy(alpha = 0.15f)
                ) {}
            }

            // Central Scanner Card
            Surface(
                modifier = Modifier.size(180.dp),
                shape = CircleShape,
                tonalElevation = 6.dp,
                border = BorderStroke(2.dp, colorResource(R.color.strat_blue).copy(alpha = 0.2f)),
                color = MaterialTheme.colorScheme.surface
            ) {
                Box(contentAlignment = Alignment.Center) {
                    // Switch icon based on state
                    AnimatedContent(targetState = screenState, label = "icon_transition") { target ->
                        val iconTint = colorResource(R.color.strat_blue)
                        when (target) {
                            is EnrollmentScreenState.CheckDuplicateFinger -> {
                                Icon(Icons.Rounded.Search, null, Modifier.size(70.dp), iconTint)
                            }
                            else -> {
                                Icon(Icons.Rounded.Fingerprint, null, Modifier.size(80.dp), iconTint)
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // 3. Dynamic Title & Description
        // AnimatedContent makes the text change feel "premium"
        AnimatedContent(
            targetState = screenState,
            transitionSpec = {
                fadeIn(tween(400)) + slideInHorizontally { it } togetherWith
                        fadeOut(tween(400)) + slideOutHorizontally { -it }
            },
            label = "text_transition"
        ) { targetState ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = when(targetState) {
                        is EnrollmentScreenState.CheckDuplicateFinger -> "Analyzing..."
                        is EnrollmentScreenState.FirstScan -> "Initial Scan"
                        is EnrollmentScreenState.SecondScan -> "Verify Fingerprint"
                        else -> "Enrolling"
                    },
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    color = colorResource(R.color.strat_blue).copy(alpha = 0.08f),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(
                        text = targetState.enrollmentMessage,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 4. Visual Step dots at the bottom
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(3) { index ->
                val stepActive = (screenState.currentStep - 3) >= index
                val color = if (stepActive) colorResource(R.color.strat_blue)
                else MaterialTheme.colorScheme.outlineVariant

                Box(
                    modifier = Modifier
                        .height(6.dp)
                        .width(if (stepActive) 24.dp else 12.dp)
                        .clip(CircleShape)
                        .background(color)
                        .animateContentSize()
                )
            }
        }
    }
}