package com.example.figerprintconsole.app.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.figerprintconsole.app.ui.screen.detail_attendance.DetailAttendanceScreen
import com.example.figerprintconsole.app.ui.screen.detail_attendance.DetailAttendanceViewModel
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
                navigateToAttendanceScreen = {
                    userDetail -> navController.navigate(Route.ATTENDANCE_SCREEN_WITH_ARGS.replace("{userServerId}", userDetail.userServerId))
                }
            )
        }

        composable(route = Route.DEVICES_SCREEN) {
            DeviceScreen(emptyList(), { })
        }

        composable(route = Route.LOGS_SCREEN) {
            LogsScreen()
        }
        composable(
            route = Route.ATTENDANCE_SCREEN_WITH_ARGS,
            arguments = listOf(navArgument("userServerId") { type = NavType.StringType })
        ) { navBackStackEntry ->

            // WoW! This is something serious for production
            // Using "!!" is a DANGER Sign!
            val userId = navBackStackEntry.arguments?.getString("userServerId")!!

            val viewModel: DetailAttendanceViewModel = hiltViewModel()
            viewModel.saveUserCode(userId)
            DetailAttendanceScreen()

        }

        composable(route = Route.SETTING_SCREEN) {

        }
    }
}
