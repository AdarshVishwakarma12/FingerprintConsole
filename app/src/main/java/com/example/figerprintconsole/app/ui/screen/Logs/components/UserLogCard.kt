package com.example.figerprintconsole.app.ui.screen.Logs.components

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
import com.example.figerprintconsole.app.domain.model.*

@Preview
@Composable
fun UserLogCard(
    log: AuthenticationLog = AuthenticationLog(
        "EMP-1011",
        user = User("EMP-1011", "Arvind Kumar Vishwakarma", "arvind@example.com", null, null, null, true, ""),
        device = Device("001", "Device-01", "Reception", "Office", "", 90, "",
            DeviceType.TERMINAL,
            DeviceStatusType.ONLINE, "192.168.1.10", "08:00", "17:00", "9h"),
        "09:12 AM",
        "2024-01-15",
        95
    ),
    modifier: Modifier = Modifier
) {
    val user = log.user ?: return

    val rowGradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0x72CDF6CF).copy(alpha = 0.1f),
            Color(0x77B1CEE3).copy(alpha = 0.3f)
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),

        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Box(
                modifier = modifier.fillMaxWidth().size(3.dp).background(Color.Green)
            )
            Column(modifier = Modifier.background(rowGradient).padding(16.dp)) {
                // Header with user info
                Row(
                    //modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(modifier = Modifier.width(12.dp))

                    // User info
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = user.fullName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "ID: ${user.employeeCode}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Confidence badge
                    ConfidenceBadge(log.confidenceScore ?: 0)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Time tracking section
                TimeTrackingSection(
                    inTime = "09:12 AM",
                    outTime = "05:48 PM",
                    totalTime = "8h 36m"
                )
            }
        }

    }
}

@Composable
fun ConfidenceBadge(score: Int) {
    val color = when {
        score >= 90 -> MaterialTheme.colorScheme.primary
        score >= 70 -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.error
    }
}

@Composable
fun TimeTrackingSection(inTime: String, outTime: String, totalTime: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
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