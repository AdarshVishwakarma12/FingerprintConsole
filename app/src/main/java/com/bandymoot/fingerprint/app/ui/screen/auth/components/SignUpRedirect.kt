package com.bandymoot.fingerprint.app.ui.screen.auth.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.bandymoot.fingerprint.app.utils.AuthColors

@Composable
fun SignUpRedirect(
    colors: AuthColors,
    onSignUpClick: () -> Unit
) {
    Row {
        Text(
            text = "Don't have an account? ",
            color = colors.secondaryText,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
        TextButton(onClick = onSignUpClick) {
            Text(
                text = "Sign up",
                color = colors.primaryText,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
