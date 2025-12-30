package com.example.figerprintconsole.app.ui.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.figerprintconsole.app.domain.model.BottomNavItem
import com.example.figerprintconsole.app.ui.navigation.Route

@Composable
fun DashboardBottomNav(
    currentRoute: String?,
    onDashboardNavigationClick: () -> Unit,
    onUsersNavigationClick: () -> Unit,
    onDeviceNavigationClick: () -> Unit,
    onLogsNavigationClick: () -> Unit,
    onSettingsNavigationClick: () -> Unit

) {

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        listOf(
            BottomNavItem(Route.HOME_SCREEN, "Dashboard", Icons.Default.Dashboard),
            BottomNavItem(Route.USER_SCREEN, "Users", Icons.Default.People),
            BottomNavItem(Route.DEVICES_SCREEN, "Devices", Icons.Default.Devices),
            BottomNavItem(Route.LOGS_SCREEN, "Logs", Icons.Default.History),
            BottomNavItem(Route.SETTING_SCREEN, "Settings", Icons.Default.Settings)
        ).forEachIndexed { index, item ->

            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    when (index) {
                        0 -> onDashboardNavigationClick()
                        1 -> onUsersNavigationClick()
                        2 -> onDeviceNavigationClick()
                        3 -> onLogsNavigationClick()
                        4 -> onSettingsNavigationClick()
                    }

                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = Color.Blue.copy(alpha = 0.12f)
                ),
            )
        }
    }
}