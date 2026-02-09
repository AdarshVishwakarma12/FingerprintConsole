package com.bandymoot.fingerprint.app.ui.screen.auth.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.utils.AuthColors

@Composable
fun SignUpHeader(colors: AuthColors) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Create an account",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colors.primaryText
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter your email to sign up for this app",
            style = MaterialTheme.typography.bodyMedium,
            color = colors.secondaryText
        )
    }
}
