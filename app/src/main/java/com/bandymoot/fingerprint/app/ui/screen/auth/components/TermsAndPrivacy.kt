package com.bandymoot.fingerprint.app.ui.screen.auth.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.utils.AuthColors

@Composable
fun TermsAndPrivacy(colors: AuthColors) {
    Text(
        text = "By clicking continue, you agree to our Terms of Service and Privacy Policy",
        color = colors.secondaryText,
        style = MaterialTheme.typography.bodySmall,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}
