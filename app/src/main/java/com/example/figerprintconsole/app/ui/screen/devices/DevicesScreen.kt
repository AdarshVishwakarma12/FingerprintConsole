package com.example.figerprintconsole.app.ui.screen.devices

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.figerprintconsole.app.ui.home.state.DeviceStatus
import com.example.figerprintconsole.app.ui.home.utils.HomeUtils
import com.example.figerprintconsole.app.ui.screen.devices.components.DeviceStatusRow
import com.example.figerprintconsole.app.ui.screen.devices.components.NoDevicesEmptyState
import com.example.figerprintconsole.app.ui.screen.devices.components.StatusDot


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


