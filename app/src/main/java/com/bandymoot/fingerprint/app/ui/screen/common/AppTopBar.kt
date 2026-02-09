package com.bandymoot.fingerprint.app.ui.screen.common

import androidx.compose.runtime.Composable
import com.bandymoot.fingerprint.app.ui.home.components.DashboardTopBar
import com.bandymoot.fingerprint.app.ui.navigation.Route
import com.bandymoot.fingerprint.app.ui.screen.devices.components.DeviceTopBar
import com.bandymoot.fingerprint.app.ui.screen.users.components.UsersListTopBar

@Composable
fun AppTopBar(
    currentRoute: String?,
) {
    when(currentRoute) {
        Route.Home.route -> {
            // This is temporary!
            DashboardTopBar(
                userName = "AD",
                showNotifications = false,
                onNotificationsClick = {  },
                onProfileClick = { },
                onSearch = {}
            )
        }
        Route.Users.route -> {
            UsersListTopBar()
        }
        Route.Devices.route -> {
            DeviceTopBar()
        }
        else -> { }
    }
}