package com.bandymoot.fingerprint.app.ui.screen.devices.state

import com.bandymoot.fingerprint.app.domain.model.Device

data class UiStateDeviceScreen (
    val isLoading: Boolean = true,
    val deviceList: List<Device> = emptyList(),
    val isErrorState: Boolean = false,
    val errorType: DeviceScreenErrorState = DeviceScreenErrorState.NoError,
    val isRefreshing: Boolean = false
)

sealed class DeviceScreenErrorState() {
    object NoError: DeviceScreenErrorState()
    class Unknown(val message: String): DeviceScreenErrorState()
}