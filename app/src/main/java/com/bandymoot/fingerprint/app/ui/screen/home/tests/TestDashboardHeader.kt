package com.bandymoot.fingerprint.app.ui.home.tests

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.bandymoot.fingerprint.app.ui.screen.home.components.DashboardHeader
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun TestDashboardHeader() {
    var isLoading by remember { mutableStateOf(false) }
    println("You've called me")
    LaunchedEffect(isLoading) {
        delay(3000)
        isLoading = false
    }
    DashboardHeader(
        {if(!isLoading) isLoading = true},
        isLoading
    )
}