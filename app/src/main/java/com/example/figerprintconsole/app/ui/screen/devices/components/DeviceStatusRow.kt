package com.example.figerprintconsole.app.ui.screen.devices.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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


@Composable
fun DeviceStatusRow(
    device: DeviceStatus,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusDot(color = HomeUtils.getDeviceColor(device.status))

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = device.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = HomeUtils.getDeviceColor(device.status)
                )
            }
        }

        Divider(modifier = Modifier.padding(top = 12.dp))
    }
}