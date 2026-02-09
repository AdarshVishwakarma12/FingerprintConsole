package com.bandymoot.fingerprint.app.ui.screen.detail_attendance.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Devices
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
import com.bandymoot.fingerprint.app.data.local.entity.AttendanceStatus
import com.bandymoot.fingerprint.app.domain.model.AttendanceRecord

@Composable
fun DeviceLogItem(
    log: AttendanceRecord,
    modifier: Modifier = Modifier
) {

    val statusColor = getStatusColor(log.status)

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {

        Column(
            modifier = Modifier.padding(14.dp)
        ) {

            // Device + Status Row
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Device icon
                Icon(
                    imageVector = Icons.Default.Devices,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = log.status.toString() ?: "Unknown Device",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )

                StatusBadgeCompact(log.status)
            }

            Spacer(Modifier.height(12.dp))

            DeviceTimeRow(log)
        }
    }
}

@Composable
fun StatusBadgeCompact(status: AttendanceStatus) {

    val color = getStatusColor(status)

    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = status.name.replace("_", " "),
            color = color,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium
        )
    }
}


fun getStatusColor(status: AttendanceStatus): Color =
    when (status) {
        AttendanceStatus.PRESENT -> Color(0xFF4CAF50)
        AttendanceStatus.ABSENT -> Color(0xFFF44336)
        AttendanceStatus.HALF_DAY -> Color(0xFF4CAF50)
        AttendanceStatus.LATE -> Color(0xFFFFC107)
        AttendanceStatus.LEAVE -> Color(0xFF2196F3)
        AttendanceStatus.HOLIDAY -> Color(0xFF9E9E9E)
        AttendanceStatus.WEEKEND -> Color(0xFF795548)
        AttendanceStatus.ON_DUTY -> Color(0xFF673AB7)
    }
