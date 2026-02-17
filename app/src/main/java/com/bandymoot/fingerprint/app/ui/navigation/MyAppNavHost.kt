package com.bandymoot.fingerprint.app.ui.navigation

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
import com.bandymoot.fingerprint.app.ui.screen.detail_attendance.DetailAttendanceScreen
import com.bandymoot.fingerprint.app.ui.screen.detail_attendance.DetailAttendanceViewModel
import com.bandymoot.fingerprint.app.ui.screen.logs.LogsScreen
import com.bandymoot.fingerprint.app.ui.screen.home.FingerprintDashboard
import com.bandymoot.fingerprint.app.ui.screen.devices.DeviceScreen
import com.bandymoot.fingerprint.app.ui.screen.enroll_device.DeviceEnrollScreen
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.StartEnrollmentProcess
import com.bandymoot.fingerprint.app.ui.screen.profile.ProfileScreen
import com.bandymoot.fingerprint.app.ui.screen.users.UsersListScreen

@Composable
fun MyAppNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavHost(
        navController = navController,
        startDestination = Route.Home.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Route.Home.route) {
            FingerprintDashboard(
                onLogsNavigationClick = { navController.navigate(Route.Logs.route) },

                onDeviceClick = { },
                onActivityClick = { },

                recentActivities = emptyList(),
                devices = emptyList(),
                currentUserName = "None",

                onRefresh = { }
            )
        }

        composable(route = Route.UserEnroll.route) {
            StartEnrollmentProcess(
                onCompleteEnrollment = { navController.navigate(Route.UserEnroll.route) },
                navBackStackEntry = navBackStackEntry
            )
        }

        composable(route = Route.Users.route) {
            UsersListScreen(
                onEnrollUser = { navController.navigate(Route.UserEnroll.route) },
                navigateToAttendanceScreen = {
                    userDetail -> navController.navigate(Route.Attendance.createRoute(userDetail.userServerId))
                }
            )
        }

        composable(route = Route.Devices.route) {
            DeviceScreen(emptyList(), { })
        }

        composable(route = Route.DeviceEnroll.route) {
            DeviceEnrollScreen()
        }

        composable(route = Route.Logs.route) {
            LogsScreen()
        }
        composable(
            route = Route.Attendance.routeWithArgs,
            arguments = listOf(navArgument("userServerId") { type = NavType.StringType })
        ) { navBackStackEntry ->

            // WoW! This is something serious for production
            // Using "!!" is a DANGER Sign!
            val userId = navBackStackEntry.arguments?.getString(Route.Attendance.argUserServerId)!!

            val viewModel: DetailAttendanceViewModel = hiltViewModel()
            viewModel.saveUserCode(userId)
            DetailAttendanceScreen()

        }

        composable(route = Route.Settings.route) {
            ProfileScreen()
        }
    }
}
