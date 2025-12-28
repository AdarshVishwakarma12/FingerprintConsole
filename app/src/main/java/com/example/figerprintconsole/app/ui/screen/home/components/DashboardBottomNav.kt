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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.figerprintconsole.app.ui.home.state.BottomNavItem

@Composable
fun DashboardBottomNav(
    onDashboardNavigationClick: () -> Unit,
    onUsersNavigationClick: () -> Unit,
    onDeviceNavigationClick: () -> Unit,
    onLogsNavigationClick: () -> Unit,
    onSettingsNavigationClick: () -> Unit

) {
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        listOf(
            BottomNavItem("Dashboard", Icons.Default.Dashboard),
            BottomNavItem("Users", Icons.Default.People),
            BottomNavItem("Devices", Icons.Default.Devices),
            BottomNavItem("Logs", Icons.Default.History),
            BottomNavItem("Settings", Icons.Default.Settings)
        ).forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index

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