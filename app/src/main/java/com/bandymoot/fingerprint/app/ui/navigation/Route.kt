package com.bandymoot.fingerprint.app.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.NotInterested
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Route(
    val route: String,
    val parentTab: String,
    val icon: ImageVector = Icons.Default.NotInterested
) {

    // Auth
    data object Login : Route("Auth/login_screen", parentTab = "Auth")

    // HOME TAB
    data object Home : Route("Home/home_screen", parentTab = "Home", icon = Icons.Default.Dashboard)

    // USERS TAB
    data object Users : Route("Users/user_screen", parentTab = "Users", icon = Icons.Default.People)
    data object UserEnroll : Route("Users/enroll_screen", parentTab = "Users")
    data object UserDetail : Route("Users/user_detail_screen", parentTab = "Users")

    data object Attendance : Route("Users/attendance_screen", parentTab = "Users") {
        const val argUserServerId = "userServerId"
        val routeWithArgs = "$route/{$argUserServerId}"
        fun createRoute(userId: String) = "$route/$userId"
    }

    // DEVICES TAB
    data object Devices : Route("Devices/devices_screen", parentTab = "Devices", icon = Icons.Default.Devices)
    data object DeviceDetail: Route("Devices/device_detail", parentTab = "Devices")
    data object DeviceEnroll: Route("Devices/device_enroll", parentTab = "Devices")

    // LOGS TAB
    data object Logs : Route("Attendance/logs_screen", parentTab = "Attendance", icon = Icons.Default.CoPresent)

    // SETTINGS TAB
    data object Settings : Route("Settings/setting_screen", parentTab = "Settings", icon = Icons.Default.Settings)


    companion object {

        fun fromString(routeStr: String): Route {
            val allRoutes = listOf(
                Login, Home, Users, UserEnroll, UserDetail,
                Attendance, Devices, Logs, Settings
            )

            return allRoutes.find { routeStr.startsWith(it.route) } ?: Home
        }

        fun isCurrentMatchWithItem(currentRoute: String?, currentItem: Route): Boolean {
            if(currentRoute == null) return false
            val currentRouteObj = fromString(currentRoute)
            return currentRouteObj.parentTab == currentItem.parentTab
        }
    }
}
