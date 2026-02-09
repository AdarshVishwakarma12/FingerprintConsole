package com.bandymoot.fingerprint.app.ui.screen.logs.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.ui.screen.logs.state.SlideDirection
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun SlideDateChange(
    currentDate: LocalDate = LocalDate.now(),
    formatter: DateTimeFormatter,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    slideDirection: SlideDirection,
    accentColor: Color = Color.DarkGray,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(26.dp)
    ) {

        ArrowButton(
            icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            color = accentColor,
            onClick = onPrevious
        )

        AnimatedContent(
            targetState = currentDate,
            transitionSpec = {
                when(slideDirection) {
                    SlideDirection.PREVIOUS -> {
                        slideInHorizontally { -it } + fadeIn() togetherWith
                                slideOutHorizontally { +it } + fadeOut()
                    }
                    SlideDirection.NEXT -> {
                        slideInHorizontally { it } + fadeIn() togetherWith
                                slideOutHorizontally { -it } + fadeOut()
                    }
                    SlideDirection.NULL -> {
                        EnterTransition.None togetherWith ExitTransition.None
                    }

                }
            },
            label = "DateSlide"
        ) { date ->
            Text(
                text = date.format(formatter),
                style = MaterialTheme.typography.titleLarge,
                color = accentColor,
                fontWeight = FontWeight.ExtraBold
            )
        }

        ArrowButton(
            icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            color = accentColor,
            onClick = onNext
        )
    }
}


@Composable
fun ArrowButton(
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.3f)) // consistent soft background
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.DarkGray
        )
    }
}
