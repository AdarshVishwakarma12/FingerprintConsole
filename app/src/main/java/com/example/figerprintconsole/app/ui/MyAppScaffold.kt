package com.example.figerprintconsole.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.figerprintconsole.app.ui.screen.common.DashboardBottomNav
import com.example.figerprintconsole.app.ui.navigation.MyAppNavHost
import com.example.figerprintconsole.app.ui.navigation.Route
import com.example.figerprintconsole.app.ui.screen.common.AppTopBar
import com.example.figerprintconsole.app.ui.screen.common.SnackBarManager
import com.example.figerprintconsole.app.utils.AppConstant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppScaffold(
    navController: NavHostController,
    currentRoute: String?,
    modifier: Modifier = Modifier,
) {

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        SnackBarManager.snackBarFlow.collect { messsage ->
            AppConstant.debugMessage("!!!SHOWING SNACK BAR NOW!!! - collected on app scaffold!!!!")
            snackBarHostState.showSnackbar(messsage)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),

        floatingActionButton = {
            when(currentRoute) {
                Route.DEVICES_SCREEN -> {
                    ExtendedFloatingActionButton(
                        onClick = { navController.navigate(Route.HOME_SCREEN) },
                        icon = { Icon(Icons.Default.Add, "Add Device") },
                        text = { Text("Add Device") },
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                else -> { }
            }
        },

        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },

        topBar = {
            AppTopBar(currentRoute)
        },

        bottomBar = {
            DashboardBottomNav(
                currentRoute,
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