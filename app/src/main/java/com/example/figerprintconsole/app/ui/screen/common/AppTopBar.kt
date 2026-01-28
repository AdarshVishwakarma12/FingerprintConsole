package com.example.figerprintconsole.app.ui.screen.common

import androidx.compose.runtime.Composable
import com.example.figerprintconsole.app.ui.home.components.DashboardTopBar
import com.example.figerprintconsole.app.ui.navigation.Route
import com.example.figerprintconsole.app.ui.screen.devices.components.DeviceTopBar
import com.example.figerprintconsole.app.ui.screen.users.components.UsersListTopBar

@Composable
fun AppTopBar(
    currentRoute: String?,
) {
    when(currentRoute) {
        Route.HOME_SCREEN -> {
            // This is temporary!
            DashboardTopBar(
                userName = "AD",
                showNotifications = false,
                onNotificationsClick = {  },
                onProfileClick = { },
                onSearch = {}
            )
        }
        Route.USER_SCREEN -> {
            UsersListTopBar()
        }
        Route.DEVICES_SCREEN -> {
            DeviceTopBar()
        }
        else -> { }
    }
}