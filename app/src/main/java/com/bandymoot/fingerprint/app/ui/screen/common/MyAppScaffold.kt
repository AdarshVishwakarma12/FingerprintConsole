package com.bandymoot.fingerprint.app.ui.screen.common

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
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
import com.bandymoot.fingerprint.app.ui.navigation.MyAppNavHost
import com.bandymoot.fingerprint.app.ui.navigation.Route
import com.bandymoot.fingerprint.app.utils.AppConstant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppScaffold(
    navController: NavHostController,
    currentRoute: String?,
    modifier: Modifier = Modifier,
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val imeOpen = rememberImeOpen() // IT WORKS! But It's a Curse!

    LaunchedEffect(Unit) {
        SnackBarManager.snackBarFlow.collect { message ->
            AppConstant.debugMessage("!!!SHOWING SNACK BAR NOW!!! - collected on app scaffold!!!!")
            snackBarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),

        floatingActionButton = {
            when(currentRoute) {
                Route.Devices.route -> {
                    ExtendedFloatingActionButton(
                        onClick = { navController.navigate(Route.Devices.route) },
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
            // BAD CODE GOES HERE! -> for future dev: find a better solution!
            if(!imeOpen) {
                DashboardBottomNav(
                    currentRoute,
                    onDashboardNavigationClick = {
                        navController.navigate(Route.Home.route) {
                            popUpTo(Route.Home.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onUsersNavigationClick = {
                        navController.navigate(Route.Users.route) {
                            launchSingleTop = true
                        }
                    },
                    onDeviceNavigationClick = {
                        navController.navigate(Route.Devices.route) {
                            launchSingleTop = true
                        }
                    },
                    onLogsNavigationClick = {
                        navController.navigate(Route.Logs.route) {
                            launchSingleTop = true
                        }
                    },
                    onSettingsNavigationClick = {
                        navController.navigate(Route.Settings.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
            // BAD CODE ENDS HERE
        },
        // Add Padding for System Bars
        contentWindowInsets = WindowInsets.systemBars,
    ) { innerPadding ->

        MyAppNavHost(navController = navController, innerPadding = innerPadding)

    }
}