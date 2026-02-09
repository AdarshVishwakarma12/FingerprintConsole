package com.bandymoot.fingerprint.app.ui.screen.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.utils.AppThemeMode

@Composable
fun RootComposable(
    currentRoute: String?,
    navController: NavHostController,
    isDark: Boolean,
    tokenProvider: TokenProvider
) {

    val tokenValue by tokenProvider.tokenFLow.collectAsState()

    if(tokenValue.isNullOrEmpty()) {
        AuthScaffold(
            themeMode = if(isDark) AppThemeMode.DARK else AppThemeMode.LIGHT,
            navController = navController,
            currentRoute = currentRoute
        )
    } else {
        MyAppScaffold(
            currentRoute = currentRoute,
            navController = navController,
            // currentTheme = if(isDark) AppThemeMode.DARK else AppThemeMode.LIGHT
        )
    }
}