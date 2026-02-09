package com.bandymoot.fingerprint.app.ui.screen.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.utils.AuthColors
import com.bandymoot.fingerprint.R

@Composable
fun SocialAuthSection(colors: AuthColors) {
    Column {
        SocialButton(
            text = "Continue with Google",
            icon = R.drawable.ic_google,
            colors = colors
        )
        Spacer(modifier = Modifier.height(12.dp))
        SocialButton(
            text = "Continue with Apple",
            icon = R.drawable.ic_apple,
            colors = colors
        )
    }
}