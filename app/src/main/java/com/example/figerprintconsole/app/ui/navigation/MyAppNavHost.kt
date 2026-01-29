package com.example.figerprintconsole.app.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.figerprintconsole.app.ui.screen.logs.LogsScreen
import com.example.figerprintconsole.app.ui.screen.home.FingerprintDashboard
import com.example.figerprintconsole.app.ui.screen.devices.DeviceScreen
import com.example.figerprintconsole.app.ui.screen.enroll.StartEnrollmentProcess
import com.example.figerprintconsole.app.ui.screen.users.UsersListScreen

@Composable
fun MyAppNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = Route.HOME_SCREEN,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Route.HOME_SCREEN) {
            FingerprintDashboard(
                onLogsNavigationClick = { navController.navigate(Route.LOGS_SCREEN) },

                onDeviceClick = { },
                onActivityClick = { },

                recentActivities = emptyList(),
                devices = emptyList(),
                currentUserName = "None",

                onRefresh = { }
            )
        }

        composable(route = Route.ENROLL_SCREEN) {
            StartEnrollmentProcess(
                onCompleteEnrollment = { navController.navigate(Route.USER_SCREEN) },
                navBackStackEntry = navBackStackEntry
            )
        }

        composable(route = Route.USER_SCREEN) {
            UsersListScreen(
                onEnrollUser = { navController.navigate(Route.ENROLL_SCREEN) },
                navigateToAttendanceScreen = { navController.navigate(Route.ATTENDANCE_SCREEN) }
            )
        }

        composable(route = Route.DEVICES_SCREEN) {
            DeviceScreen(emptyList(), { })
        }

        composable(route = Route.LOGS_SCREEN) {
            LogsScreen()
        }
        composable(route = Route.ATTENDANCE_SCREEN) {

        }

        composable(route = Route.SETTING_SCREEN) {

        }
    }
}
