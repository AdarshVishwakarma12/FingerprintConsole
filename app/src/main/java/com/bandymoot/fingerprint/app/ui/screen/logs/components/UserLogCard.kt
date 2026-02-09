package com.bandymoot.fingerprint.app.ui.screen.logs.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.data.local.entity.AttendanceStatus
import com.bandymoot.fingerprint.app.domain.model.*

@Preview
@Composable
fun UserLogCard(
    log: AttendanceRecord = AttendanceRecord("Arvind", "EMP-1011", "10.09.2026", "10:40", "06:50", "09Min", "30Min", "0Min",
        AttendanceStatus.HALF_DAY, null),
    modifier: Modifier = Modifier
) {
    // Map status to colors
    val statusColor = when (log.status) {
        AttendanceStatus.PRESENT -> Color(0xFF4CAF50)      // Green
        AttendanceStatus.ABSENT -> Color(0xFFF44336)       // Red
        AttendanceStatus.HALF_DAY -> null                  // Gradient handled separately
        AttendanceStatus.LATE -> Color(0xFFFFC107)         // Amber
        AttendanceStatus.LEAVE -> Color(0xFF2196F3)        // Blue
        AttendanceStatus.HOLIDAY -> Color(0xFF9E9E9E)      // Gray
        AttendanceStatus.WEEKEND -> Color(0xFF795548)      // Brown
        AttendanceStatus.ON_DUTY -> Color(0xFF673AB7)     // Purple
    }

    // Upper box gradient for HALF_DAY
    val topBarBrush = when (log.status) {
        AttendanceStatus.HALF_DAY -> Brush.horizontalGradient(
            colors = listOf(Color(0xFF7FD282), Color(0xFFECECEC))
        )
        else -> Brush.horizontalGradient(colors = listOf(statusColor ?: Color.Gray, statusColor ?: Color.Gray))
    }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            // Upper colored bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .background(topBarBrush)
            )

            // Content
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                // Header: User info + employee ID
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = log.userName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "ID: ${log.employeeId}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Status badge
                    StatusBadge(status = log.status)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Time tracking section
                TimeTrackingSection(
                    inTime = log.checkInTime,
                    outTime = log.checkOutTime,
                    totalTime = log.totalWorkingTime
                )

                // Remark if any
                log.remark?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Remark: $it",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: AttendanceStatus) {
    val (text, color) = when(status) {
        AttendanceStatus.PRESENT -> "PRESENT" to Color(0xFF4CAF50)
        AttendanceStatus.ABSENT -> "ABSENT" to Color(0xFFF44336)
        AttendanceStatus.HALF_DAY -> "HALF DAY" to Color(0xFF4CAF50)
        AttendanceStatus.LATE -> "LATE" to Color(0xFFFFC107)
        AttendanceStatus.LEAVE -> "LEAVE" to Color(0xFF2196F3)
        AttendanceStatus.HOLIDAY -> "HOLIDAY" to Color(0xFF9E9E9E)
        AttendanceStatus.WEEKEND -> "WEEKEND" to Color(0xFF795548)
        AttendanceStatus.ON_DUTY -> "ON DUTY" to Color(0xFF673AB7)
    }

    Box(
        modifier = Modifier
            .background(color.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun TimeTrackingSection(inTime: String, outTime: String, totalTime: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TimeBlock(
                icon = Icons.Default.Login,
                label = "IN",
                time = inTime,
                color = MaterialTheme.colorScheme.primary
            )

            Divider(
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )

            TimeBlock(
                icon = Icons.Default.Logout,
                label = "OUT",
                time = outTime,
                color = MaterialTheme.colorScheme.tertiary
            )

            Divider(
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )

            TimeBlock(
                icon = Icons.Default.AccessTime,
                label = "TOTAL",
                time = totalTime,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun TimeBlock(
    icon: ImageVector,
    label: String,
    time: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.widthIn(min = 80.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = time,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
