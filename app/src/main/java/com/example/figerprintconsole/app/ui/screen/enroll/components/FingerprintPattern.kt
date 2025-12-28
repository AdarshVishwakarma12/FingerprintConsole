package com.example.figerprintconsole.app.ui.enroll.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun FingerprintPattern(
    isAnimating: Boolean,
    progress: Float
) {

    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing)
        )
    )

    val primaryColor = MaterialTheme.colorScheme.primary

    Canvas(modifier = Modifier.fillMaxSize()) {
        // Draw fingerprint ridges
        val centerX = size.width / 2
        val centerY = size.height / 2
        val maxRadius = size.minDimension * 0.4f

        for(i in 0..10) {
            val radius = maxRadius * (i / 10f) * progress
            val angleOffset = if (isAnimating) rotation else 0f

            drawCircle(
                color = primaryColor.copy(alpha = 0.1f + 0.08f * i),
                center = center,
                radius = radius,
                style = Stroke(width = 2f)
            )


            // Draw ridge segments
            for (j in 0..7) {
                val angle = (j * 45f + angleOffset) * (Math.PI / 180).toFloat()
                val startX = centerX + radius * cos(angle)
                val startY = centerY + radius * sin(angle)
                val endX = centerX + (radius + 20f) * cos(angle)
                val endY = centerY + (radius + 20f) * sin(angle)

                drawLine(
                    color = primaryColor.copy(alpha = 0.3f),
                    start = androidx.compose.ui.geometry.Offset(startX, startY),
                    end = androidx.compose.ui.geometry.Offset(endX, endY),
                    strokeWidth = 2f
                )
            }
        }
    }
}