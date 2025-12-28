package com.example.figerprintconsole.app.ui.navigation
//
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.figerprintconsole.app.ui.home.FingerprintDashboard
import com.example.figerprintconsole.app.ui.testScreen.TestScreen

//
@Composable
fun MyAppNavHost() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.HOME_SCREEN
    ) {
        composable(route = Route.HOME_SCREEN) {
            TestScreen(

            )
        }
    }
}
