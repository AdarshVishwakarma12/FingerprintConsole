package com.bandymoot.fingerprint.app.ui.screen.common

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bandymoot.fingerprint.app.ui.navigation.Route

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
            Route.Home,
            Route.Users,
            Route.Devices,
            Route.Logs,
            Route.Settings
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
                        contentDescription = item.parentTab,
                        tint = if(Route.isCurrentMatchWithItem(currentRoute, item)) Color.Blue else Color.Gray
                    )
                },
                label = { Text(item.parentTab) },
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