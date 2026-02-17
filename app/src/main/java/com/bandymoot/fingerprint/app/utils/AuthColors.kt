package com.bandymoot.fingerprint.app.utils

import androidx.compose.ui.graphics.Color

data class AuthColors(
    val background: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val buttonBackground: Color,
    val buttonText: Color,
    val surface: Color,
    val border: Color,
    val iconTint: Color
)

fun authColors(theme: AppThemeMode = AppThemeMode.LIGHT): AuthColors {
    return if (theme == AppThemeMode.DARK) {
        AuthColors(
            background = Color(0xFF121212),
            primaryText = Color.White,
            secondaryText = Color(0xFFB0B0B0),
            buttonBackground = Color.White,
            buttonText = Color.Black,
            surface = Color(0xFF1E1E1E),
            border = Color(0xFF2C2C2C),
            iconTint = Color.White
        )
    } else {
        AuthColors(
            background = Color.White,
            primaryText = Color.Black,
            secondaryText = Color.Gray,
            buttonBackground = Color.Black,
            buttonText = Color.White,
            surface = Color(0xFFF5F5F5),
            border = Color.LightGray,
            iconTint = Color.Black
        )
    }
}