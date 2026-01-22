package com.example.figerprintconsole.app.ui.screen.devices

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.figerprintconsole.app.domain.model.DeviceStatus
import com.example.figerprintconsole.app.ui.screen.devices.components.DeviceStatusRow
import com.example.figerprintconsole.app.ui.screen.devices.components.NoDevicesEmptyState


@Composable
fun DeviceScreen(
    devices: List<DeviceStatus>,
    onDeviceClick: (String) -> Unit,
    deviceScreenViewModel: DeviceScreenViewModel = hiltViewModel()
) {

    val uiStateDeviceScreen by deviceScreenViewModel.uiStateDeviceScreen.collectAsState()

    if(uiStateDeviceScreen.deviceList.isEmpty()) {
        NoDevicesEmptyState()
    } else {
        LazyColumn {
            items(uiStateDeviceScreen.deviceList) { device ->
                DeviceStatusRow(
                    device = device,
                    onClick = { onDeviceClick(device.deviceCode) }
                )
            }
        }
    }
}