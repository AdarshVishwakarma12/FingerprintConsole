package com.bandymoot.fingerprint.app.ui.screen.auth.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.bandymoot.fingerprint.app.utils.AuthColors

@Composable
fun AppTitle(
    text: String,
    colors: AuthColors,
) {

    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        color = colors.primaryText
    )
}

