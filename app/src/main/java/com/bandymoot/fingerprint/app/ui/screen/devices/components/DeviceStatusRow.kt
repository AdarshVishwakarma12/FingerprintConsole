package com.bandymoot.fingerprint.app.ui.screen.devices.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryAlert
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DoorFront
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.domain.model.Device
import com.bandymoot.fingerprint.app.domain.model.DeviceType

@Composable
fun DeviceStatusRow(
    device: Device,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick(device.serverDeviceId) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Device Type Icon with a subtle background
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when(device.type) {
                        DeviceType.TERMINAL -> Icons.Default.PointOfSale
                        DeviceType.MOBILE -> Icons.Default.Smartphone
                        DeviceType.DESKTOP -> Icons.Default.Computer
                        DeviceType.GATE -> Icons.Default.DoorFront
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // 2. Middle Content: Name and Location
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = device.name ?: "Unnamed Device",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = device.location ?: "Unknown Location",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // 3. End Content: Status and Battery
            Column(horizontalAlignment = Alignment.End) {
                StatusBadge(status = device.status)

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val batteryIcon = when {
                        (device.batteryLevel ?: 0) > 80 -> Icons.Default.BatteryFull
                        (device.batteryLevel ?: 0) > 20 -> Icons.Default.BatteryChargingFull
                        else -> Icons.Default.BatteryAlert
                    }
                    Icon(
                        imageVector = batteryIcon,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = if ((device.batteryLevel ?: 0) < 20) Color.Red else Color.Gray
                    )
                    Text(
                        text = "${device.batteryLevel ?: 0}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}