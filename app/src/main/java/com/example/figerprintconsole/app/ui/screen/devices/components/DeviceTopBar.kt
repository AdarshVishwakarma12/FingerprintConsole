package com.example.figerprintconsole.app.ui.screen.devices.components

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.figerprintconsole.app.ui.screen.devices.DeviceScreenViewModel
import com.example.figerprintconsole.app.ui.screen.users.UserScreenViewModel
import com.example.figerprintconsole.app.ui.screen.users.state.SearchQueryUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceTopBar(
    userScreenViewModel: DeviceScreenViewModel = hiltViewModel()
) {
    // val uiState by userScreenViewModel.uiState.collectAsState()

    TopAppBar(
        title = {
            Text(
                text = "Devices",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        },
        modifier = Modifier.background(color = Color.Blue.copy(alpha = 0.6f))
    )
}