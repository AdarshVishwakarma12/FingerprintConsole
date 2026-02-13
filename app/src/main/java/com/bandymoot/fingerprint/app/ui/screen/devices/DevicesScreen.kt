package com.bandymoot.fingerprint.app.ui.screen.devices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.bandymoot.fingerprint.app.domain.model.DeviceStatus
import com.bandymoot.fingerprint.app.ui.screen.devices.components.DeviceStatusRow
import com.bandymoot.fingerprint.app.ui.screen.devices.components.NoDevicesEmptyState
import com.bandymoot.fingerprint.app.ui.screen.devices.event.UiEventDeviceEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceScreen(
    devices: List<DeviceStatus>,
    onDeviceClick: (String) -> Unit,
    deviceScreenViewModel: DeviceScreenViewModel = hiltViewModel()
) {
    // Collecting state from the ViewModel
    val uiStateDeviceScreen by deviceScreenViewModel.uiStateDeviceScreen.collectAsState()

    // PullToRefreshBox handles the layout and the indicator visibility
    PullToRefreshBox(
        isRefreshing = uiStateDeviceScreen.isRefreshing,
        onRefresh = {
            deviceScreenViewModel.onEvent(UiEventDeviceEvent.PullToRefresh)
        },
        modifier = Modifier.fillMaxSize()
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
                        onClick = { onDeviceClick(device.deviceCode) }
                    )
                }
            }
        }
    }
}