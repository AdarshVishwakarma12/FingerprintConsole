package com.bandymoot.fingerprint.app.ui.screen.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.bandymoot.fingerprint.app.ui.navigation.AuthNavHost
import com.bandymoot.fingerprint.app.utils.AppThemeMode

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthScaffold(
    themeMode: AppThemeMode,
    navController: NavHostController,
    currentRoute: String?,
    modifier: Modifier = Modifier
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {}
    ) { innerPadding ->
        AuthNavHost(navController, innerPadding, themeMode)
    }
}