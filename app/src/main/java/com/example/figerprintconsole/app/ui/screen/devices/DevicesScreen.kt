package com.example.figerprintconsole.app.ui.screen.devices

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.figerprintconsole.app.domain.model.DeviceStatus
import com.example.figerprintconsole.app.ui.screen.devices.components.DeviceStatusRow
import com.example.figerprintconsole.app.ui.screen.devices.components.NoDevicesEmptyState


@Composable
fun DeviceScreen(
    devices: List<DeviceStatus>,
    onDeviceClick: (String) -> Unit
) {
    if(devices.isEmpty()) {
        NoDevicesEmptyState()
    } else {
        LazyColumn {
            items(devices) { device ->
                DeviceStatusRow(
                    device = device,
                    onClick = { onDeviceClick(device.id) }
                )
            }
        }
    }
}


