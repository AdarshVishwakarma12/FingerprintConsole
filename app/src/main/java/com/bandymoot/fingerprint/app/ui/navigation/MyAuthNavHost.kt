package com.bandymoot.fingerprint.app.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bandymoot.fingerprint.app.ui.screen.auth.LoginScreen
import com.bandymoot.fingerprint.app.utils.AppThemeMode

@Composable
fun AuthNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
    currentThemeMode: AppThemeMode = AppThemeMode.LIGHT
) {
    NavHost(
        navController = navController,
        startDestination = AuthRoute.LOGIN_SCREEN
    ) {
        composable(route = AuthRoute.LOGIN_SCREEN) {
            LoginScreen(
                currentTheme = currentThemeMode,
                onSignUpClick = { navController.navigate(AuthRoute.SIGNUP_SCREEN) }
            )
        }

        composable(route = AuthRoute.SIGNUP_SCREEN) { }
    }
}