package com.bandymoot.fingerprint.app.ui.screen.users.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object UserUtils {
    fun contentColorFor(background: Color): Color {
        return if (background.luminance() > 0.5f) {
            Color.Black
        } else {
            Color.White
        }
    }

    // Helper functions
    fun getAvatarColor(name: String): Color {
        val colors = listOf(
            Color(0xFFE91E63), // Pink
            Color(0xFF9C27B0), // Purple
            Color(0xFF3F51B5), // Indigo
            Color(0xFF2196F3), // Blue
            Color(0xFF00BCD4), // Cyan
            Color(0xFF4CAF50), // Green
            Color(0xFFFF9800), // Orange
            Color(0xFF795548)  // Brown
        )
        val index = name.hashCode() % colors.size
        return colors[if (index < 0) -index else index]
    }

    fun formatDate(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm")
        return date.format(formatter)
    }
}