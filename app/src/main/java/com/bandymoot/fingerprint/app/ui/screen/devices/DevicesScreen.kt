package com.bandymoot.fingerprint.app.ui.screen.devices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bandymoot.fingerprint.app.ui.screen.devices.components.DeviceDialogManager
import com.bandymoot.fingerprint.app.ui.screen.devices.components.DeviceStatusRow
import com.bandymoot.fingerprint.app.ui.screen.devices.components.NoDevicesEmptyState
import com.bandymoot.fingerprint.app.ui.screen.devices.event.DeviceUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceScreen(
    onEnrollDevice: () -> Unit,
    deviceScreenViewModel: DeviceScreenViewModel = hiltViewModel()
) {
    // Collecting state from the ViewModel
    val uiStateDeviceScreen by deviceScreenViewModel.uiStateDeviceScreen.collectAsState()

    // PullToRefreshBox handles the layout and the indicator visibility
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onEnrollDevice,
                icon = { Icon(Icons.Default.Add, "Add Device") },
                text = { Text("Add Device") },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = uiStateDeviceScreen.isRefreshing,
            onRefresh = {
                deviceScreenViewModel.onEvent(DeviceUiEvent.OnPullToRefresh)
            },
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            if (uiStateDeviceScreen.deviceList.isEmpty()) {
                // Ensure this state fills the size to allow the pull gesture
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NoDevicesEmptyState()
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiStateDeviceScreen.deviceList) { device ->
                        DeviceStatusRow(
                            device = device,
                            onClick = { deviceServerId -> deviceScreenViewModel.onEvent(DeviceUiEvent.OnDeviceClick(deviceServerId)) }
                        )
                    }
                }
            }
        }

        // Alert Dialog
        DeviceDialogManager(
            state = uiStateDeviceScreen.deviceAlertDialog,
            onEvent = { event -> deviceScreenViewModel.onEvent(event) }
        )
    }
}