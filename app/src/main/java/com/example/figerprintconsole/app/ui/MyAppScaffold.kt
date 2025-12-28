package com.example.figerprintconsole.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.figerprintconsole.app.ui.home.components.DashboardBottomNav
import com.example.figerprintconsole.app.ui.home.components.DashboardTopBar
import com.example.figerprintconsole.app.ui.navigation.MyAppNavHost
import com.example.figerprintconsole.app.ui.navigation.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppScaffold(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),

        floatingActionButton = { },

        bottomBar = {
            DashboardBottomNav(
                onDashboardNavigationClick = {
                    navController.navigate(Route.HOME_SCREEN) {
                        popUpTo(Route.HOME_SCREEN) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onUsersNavigationClick = {
                    navController.navigate(Route.USER_SCREEN) {
                        launchSingleTop = true
                    }
                },
                onDeviceNavigationClick = {
                    navController.navigate(Route.DEVICES_SCREEN) {
                        launchSingleTop = true
                    }
                },
                onLogsNavigationClick = {
                    navController.navigate(Route.LOGS_SCREEN) {
                        launchSingleTop = true
                    }
                },
                onSettingsNavigationClick = {
                    navController.navigate(Route.SETTING_SCREEN) {
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { innerPadding ->

        MyAppNavHost(navController = navController, innerPadding = innerPadding)

    }
}