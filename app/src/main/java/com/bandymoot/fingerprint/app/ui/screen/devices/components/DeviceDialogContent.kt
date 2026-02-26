package com.bandymoot.fingerprint.app.ui.screen.devices.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.domain.model.Device
import com.bandymoot.fingerprint.app.domain.model.DeviceStatusType
import com.bandymoot.fingerprint.app.ui.screen.home.utils.HomeUtils

@Composable
fun DeviceDetailContent(device: Device) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Section 1: Quick Status Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusBadge(status = device.status)
            Text(
                text = "Battery: ${device.batteryLevel ?: "--"}%",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

        // Section 2: General Information
        InfoRow(label = "Name", value = device.name ?: "Unknown")
        InfoRow(label = "Location", value = device.location ?: "Not Set")
        InfoRow(label = "Type", value = device.type.name)
        InfoRow(label = "Last Seen", value = device.lastSeenAt)

        // Section 3: Technical/Secret Info (Styled differently to show importance)
        Text(
            text = "Technical Specifications",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 8.dp)
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                InfoRow(label = "Vendor", value = device.vendor)
                InfoRow(label = "Algorithm", value = device.supportedAlgorithm)
                InfoRow(label = "Template v.", value = device.templateVersion)
            }
        }

        SecretRow(label = "Secret Key", secret = device.secretKey)
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
    }
}

// The CHEAP SOLUTION!!
// Cause making it survival takes a lot of time!
// And kind of unnecessary - Just make default { false }, no more rocket science.
@Composable
private fun SecretRow(
    label: String,
    secret: String
) {
    var isSecretVisible by remember { mutableStateOf(false) }

    Surface(
        onClick = { isSecretVisible = !isSecretVisible },
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFECEFF1), // Muted Slate Bg
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp)
            )

            // The "Pill" container for the secret

            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = if (isSecretVisible) secret else "••••••••••••",
                    modifier = Modifier
                        .fillMaxWidth(0.7f) // (1) Limit the "Squeeze" to 70% of parent width
                        .horizontalScroll(rememberScrollState()), // (2) Enable horizontal swiping
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily.Monospace
                    ),
                    color = Color(0xFF455A64),
                    fontWeight = FontWeight.Bold,
                    maxLines = 1, // (3) Force it to stay on one line
                    softWrap = false // (4) Prevent it from wrapping to a new line
                )

                Icon(
                    imageVector = if (isSecretVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun StatusBadge(status: DeviceStatusType) {
    val color = HomeUtils.getDeviceColor(status)
    Surface(
        color = color.copy(alpha = 0.15f),
        shape = CircleShape,
        border = BorderStroke(1.dp, color.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(color, CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = status.name,
                style = MaterialTheme.typography.labelSmall,
                color = color,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
@Composable
fun DeleteConfirmationContent(deviceName: String) {
    Text("Are you sure you want to permanently remove $deviceName from the console?")
}