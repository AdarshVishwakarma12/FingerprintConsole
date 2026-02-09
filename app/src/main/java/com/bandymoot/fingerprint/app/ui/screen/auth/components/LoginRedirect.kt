package com.bandymoot.fingerprint.app.ui.screen.auth.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bandymoot.fingerprint.app.utils.AuthColors

@Composable
fun LoginRedirect(
    colors: AuthColors,
    onLoginClick: () -> Unit
) {
    Row {
        Text(
            text = "Already have an account? ",
            color = colors.secondaryText,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
        TextButton(onClick = onLoginClick) {
            Text(
                text = "Log in",
                color = colors.primaryText,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
