package com.bandymoot.fingerprint.app.ui.screen.devices.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bandymoot.fingerprint.app.ui.screen.devices.event.DeviceUiEvent
import com.bandymoot.fingerprint.app.ui.screen.devices.state.DeviceDialogStage
import com.bandymoot.fingerprint.app.ui.screen.devices.state.DeviceDialogStatus
import com.bandymoot.fingerprint.app.ui.screen.devices.state.DeviceDialogUiState

@Composable
fun DeviceDialogManager(
    state: DeviceDialogUiState,
    onEvent: (DeviceUiEvent) -> Unit
) {
    val visibleState = state as? DeviceDialogUiState.Visible ?: return

    when (val status = visibleState.status) {
        is DeviceDialogStatus.Loading -> {
            DeviceDialogBase(
                title = "Processing...",
                onDismissRequest = { }, // Disable dismiss during loading for safety -> DANGEROUS!!! MOVE WITH CAUTION! USER CAN BE STUCK FOREVER!!
                confirmButton = { }
            ) { DeviceDialogLoadingContent() }
        }

        is DeviceDialogStatus.Error -> {
            DeviceDialogBase(
                title = "Error",
                onDismissRequest = { onEvent(DeviceUiEvent.OnDismissDialog) },
                confirmButton = {
                    TextButton(onClick = { onEvent(DeviceUiEvent.OnDismissDialog) }) { Text("Close") }
                }
            ) { DeviceDialogErrorContent("Something went wrong.") }
        }

        is DeviceDialogStatus.Success -> {
            // DATA IS NOW ACCESSED SAFELY INSIDE SUCCESS
            val device = status.device
            val isDetails = visibleState.stage is DeviceDialogStage.Details

            DeviceDialogBase(
                title = if (isDetails) device.deviceCode else "Confirm Delete",
                onDismissRequest = { onEvent(DeviceUiEvent.OnDismissDialog) },
                confirmButton = {
                    val (label, event) = if (isDetails) {
                        "Dismiss" to DeviceUiEvent.OnDismissDialog
                    } else {
                        "Cancel" to DeviceUiEvent.OnDeleteCancel
                    }
                    TextButton(onClick = { onEvent(event) }) { Text(label) }
                },
                dismissButton = {
                    if (isDetails) {
                        TextButton(onClick = { onEvent(DeviceUiEvent.OnDeleteClick) }) {
                            Text("Delete", color = Color.Red)
                        }
                    } else {
                        Button(
                            onClick = { onEvent(DeviceUiEvent.OnConfirmDelete(device.serverDeviceId)) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.6f))
                        ) { Text("Yes, Delete") }
                    }
                }
            ) {
                if (isDetails) {
                    DeviceDetailContent(device)
                } else {
                    DeleteConfirmationContent(device.deviceCode)
                }
            }
        }
    }
}