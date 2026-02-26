package com.bandymoot.fingerprint.app.ui.screen.users.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs

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
            Color(0xAAD50000), Color(0xAAD32F2F), Color(0xAAEF5350),
            Color(0xAAFF5252), Color(0xAAFF1744), Color(0xAAFF8A80),
            Color(0xAAC2185B), Color(0xAAE91E63), Color(0xAAF06292),
            Color(0xAAEC407A), Color(0xAAAD1457), Color(0xAA00ACC1),
            Color(0xAA6A1B9A), Color(0xAA7B1FA2), Color(0xAA8E24AA),
            Color(0xAACE93D8), Color(0xAAEA80FC), Color(0xAA6200EA),
            Color(0xAA4527A0), Color(0xAA512DA8), Color(0xAA673AB7),
            Color(0xAAB39DDB), Color(0xAA9FA8DA), Color(0xAA3F51B5),
            Color(0xAA0D47A1), Color(0xAA1976D2), Color(0xAA1E88E5),
            Color(0xAA82B1AA), Color(0xAA90CAF9), Color(0xAA5C6BC0),
            Color(0xAA0288D1), Color(0xAA29B6F6), Color(0xAA80DEEA),
            Color(0xAA26C6DA), Color(0xAA42A5F5), Color(0xAA283593),
            Color(0xAA006064), Color(0xAA0097A7), Color(0xAA00796B),
            Color(0xAA1B5E20), Color(0xAA388E3C), Color(0xAA43A047),
            Color(0xAAA5D6A7), Color(0xAA00E676), Color(0xAA26A69A),
            Color(0xAA689F38), Color(0xAA9CCC65), Color(0xAAC0CA33),
            Color(0xAAE6EE9C), Color(0xAA827717), Color(0xAAD4E157),
            Color(0xAAF9A825), Color(0xAAFBC02D), Color(0xAA66BB6A),
            Color(0xAAFFF59D), Color(0xAAFDD835), Color(0xAAFFEE58),
            Color(0xAAF57C00), Color(0xAAFFA000), Color(0xAAFFA726),
            Color(0xAAE64A19), Color(0xAA78909C), Color(0xAABDBDBD),
            Color(0xAA5D4037), Color(0xAA8D6E63), Color(0xAAFF7043),
            Color(0xAA455A64), Color(0xAA546E7A), Color(0xAA616161),
            Color(0xAA7E57C2), Color(0xAAAB47BC), Color(0xF1AB50B0),
        )

        // absolute value to simplify the negative check
        val index = abs(name.hashCode()) % colors.size
        return colors[index]
    }

    fun formatDate(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm")
        return date.format(formatter)
    }
}