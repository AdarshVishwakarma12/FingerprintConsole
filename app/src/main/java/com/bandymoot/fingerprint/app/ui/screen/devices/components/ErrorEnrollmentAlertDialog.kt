package com.bandymoot.fingerprint.app.ui.screen.devices.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.bandymoot.fingerprint.R
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentErrorState

@Composable
fun ErrorEnrollmentAlertDialog(
    error: EnrollmentErrorState,
    onRetry: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* Handle if needed */ },
        icon = { Icon(Icons.Rounded.ErrorOutline, null, tint = MaterialTheme.colorScheme.error) },
        title = { Text("Enrollment Issue") },
        text = { Text(error.message) },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text("Try Again", color = colorResource(R.color.strat_blue).copy(alpha = 2f))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss", color = Color.Red.copy(alpha = 0.75f))
            }
        }
    )
}