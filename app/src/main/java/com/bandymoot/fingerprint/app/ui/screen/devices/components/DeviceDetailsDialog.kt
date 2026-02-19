package com.bandymoot.fingerprint.app.ui.screen.devices.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.bandymoot.fingerprint.app.domain.model.Device

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceDetailsDialog(
    device: Device,
    onDismiss: () -> Unit,
    onDelete: (String) -> Unit,
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        icon = { },
        title = {
            Text(
                text = device.deviceCode,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        },
        text = { },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Dismiss")}
        },
        dismissButton = {
            TextButton(onClick = { onDelete(device.serverDeviceId) }) { Text("Delete", color = Color.Red.copy(alpha = 0.9f)) }
        }
    )
}