package com.bandymoot.fingerprint.app.ui.screen.common

import androidx.compose.runtime.Composable
import com.bandymoot.fingerprint.app.ui.home.components.DashboardTopBar
import com.bandymoot.fingerprint.app.ui.navigation.Route
import com.bandymoot.fingerprint.app.ui.screen.devices.components.DeviceTopBar
import com.bandymoot.fingerprint.app.ui.screen.enroll_device.component.DeviceEnrollmentTopBar
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.components.UserEnrollmentTopBar
import com.bandymoot.fingerprint.app.ui.screen.logs.components.AttendanceTopBar
import com.bandymoot.fingerprint.app.ui.screen.profile.components.ProfileTopBar
import com.bandymoot.fingerprint.app.ui.screen.users.components.UsersListTopBar

@Composable
fun AppTopBar(
    currentRoute: String?,
    backOperation: () -> Unit
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
        Route.Logs.route -> {
            AttendanceTopBar()
        }
        Route.Settings.route -> {
            ProfileTopBar()
        }
        Route.UserEnroll.route -> {
            UserEnrollmentTopBar(backOperation) // Added popFromBackStack!!
        }
        Route.DeviceEnroll.route -> {
            DeviceEnrollmentTopBar(backOperation)
        }
        else -> { }
    }
}