package com.example.figerprintconsole.app.ui.screen.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import java.time.LocalDateTime
import androidx.compose.material3.MaterialTheme
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.figerprintconsole.app.ui.home.components.DashboardTopBar
import com.example.figerprintconsole.app.ui.screen.home.components.DevicesStatusSection
import com.example.figerprintconsole.app.ui.home.components.NotificationsPanel
import com.example.figerprintconsole.app.ui.home.components.RecentActivitiesSection
import com.example.figerprintconsole.app.domain.model.ActivityAction
import com.example.figerprintconsole.app.domain.model.DashboardStats
import com.example.figerprintconsole.app.domain.model.Device
import com.example.figerprintconsole.app.domain.model.DeviceStatus
import com.example.figerprintconsole.app.domain.model.DeviceStatusType
import com.example.figerprintconsole.app.domain.model.DeviceType
import com.example.figerprintconsole.app.domain.model.QuickAction
import com.example.figerprintconsole.app.domain.model.RecentActivity
import com.example.figerprintconsole.app.ui.screen.home.components.DashboardHeader
import com.example.figerprintconsole.app.ui.screen.home.state.UiHomeDashboardState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FingerprintDashboard(
    onLogsNavigationClick: () -> Unit,
    onDeviceClick: (String) -> Unit,
    onActivityClick: (String) -> Unit,

    recentActivities: List<RecentActivity>,
    devices: List<DeviceStatus>,

    currentUserName: String,

    onRefresh: () -> Unit,
    fingerprintDashboardViewmodel: FingerprintDashboardViewmodel = hiltViewModel(),
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {

    val uiState by fingerprintDashboardViewmodel.uiStateHomeDashboard.collectAsState()

    val devicesList = uiState.deviceList

    var showNotifications by remember { mutableStateOf(false) }
    var showUserMenu by remember { mutableStateOf(false) }


    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val isLargeScreen = maxWidth > 800.dp

        Column(

        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = if (isLargeScreen) 32.dp else 16.dp)
                    .padding(top = 16.dp, bottom = 10.dp)
            ) {

                // Header with greeting
                DashboardHeader(
                    onRefresh = onRefresh,
                    isLoading = isLoading
                )

                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    DevicesStatusSection(
                        devices = devicesList ,
                        onDeviceClick = onDeviceClick
                    )

                    RecentActivitiesSection(
                        activities = recentActivities,
                        onActivityClick = onActivityClick,
                        onViewAllClick = { onLogsNavigationClick }
                    )
                }
            }
        }

        // Notifications Panel
        if (showNotifications) {
            NotificationsPanel(
                onDismiss = { showNotifications = false },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFingerprintDashboard() {
    MaterialTheme {
        val sampleStats = DashboardStats(
            totalUsers = 247,
            enrolledUsers = 189,
            recentScans = 1243,
            successRate = 0.983,
            activeDevices = 8,
            pendingEnrollments = 12
        )

        val sampleActivities = remember {
            List(10) { i ->
                RecentActivity(
                    id = "$i",
                    userId = "user$i",
                    userName = listOf(
                        "Adarsh",
                        "Arvind",
                        "Afnan",
                        "Ayushi"
                    )[i % 4],
                    action = ActivityAction.entries[i % ActivityAction.entries.size],
                    timestamp = LocalDateTime.now().minusMinutes((i * 30).toLong()),
                    success = i % 5 != 0,
                    deviceId = if (i % 2 == 0) "DEV-${1000 + i}" else null
                )
            }
        }

        val colorScheme = MaterialTheme.colorScheme

        val quickActions = remember {
            listOf(
                QuickAction(
                    id = "1",
                    title = "New User",
                    description = "Add new user",
                    icon = Icons.Default.PersonAdd,
                    color = colorScheme.primary,
                    route = "/users/new"
                ),
                QuickAction(
                    id = "2",
                    title = "Enroll",
                    description = "Enroll fingerprint",
                    icon = Icons.Default.Fingerprint,
                    color = colorScheme.secondary,
                    route = "/enroll"
                ),
                QuickAction(
                    id = "3",
                    title = "Reports",
                    description = "View reports",
                    icon = Icons.Default.Analytics,
                    color = colorScheme.tertiary,
                    route = "/reports"
                ),
                QuickAction(
                    id = "4",
                    title = "Settings",
                    description = "System settings",
                    icon = Icons.Default.Settings,
                    color = colorScheme.primaryContainer,
                    route = "/settings"
                )
            )
        }

        val sampleDevices = remember {
            listOf(
                DeviceStatus(
                    id = "1",
                    name = "Main Entrance",
                    type = DeviceType.GATE,
                    status = DeviceStatusType.ONLINE,
                    lastActive = LocalDateTime.now().minusMinutes(5),
                    connectedUsers = 45,
                    location = "Building A"
                ),
                DeviceStatus(
                    id = "2",
                    name = "Server Room",
                    type = DeviceType.TERMINAL,
                    status = DeviceStatusType.ONLINE,
                    lastActive = LocalDateTime.now().minusMinutes(2),
                    connectedUsers = 12,
                    location = "Floor 3"
                ),
                DeviceStatus(
                    id = "3",
                    name = "Mobile Scanner",
                    type = DeviceType.MOBILE,
                    status = DeviceStatusType.MAINTENANCE,
                    lastActive = LocalDateTime.now().minusHours(2),
                    connectedUsers = 8,
                    location = "Security Office"
                )
            )
        }

        FingerprintDashboard(
            onLogsNavigationClick = {},
            onDeviceClick = { },
            recentActivities = sampleActivities,
            devices = sampleDevices,
            onActivityClick = { },
            onRefresh = { },
            currentUserName = "None"
        )
    }
}