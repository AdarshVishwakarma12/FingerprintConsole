package com.example.figerprintconsole.app.ui.screen.devices.state

import com.example.figerprintconsole.app.domain.model.Device

data class UiStateDeviceScreen (
    val isLoading: Boolean = true,
    val deviceList: List<Device> = emptyList(),
    val isErrorState: Boolean = false,
    val errorType: DeviceScreenErrorState = DeviceScreenErrorState.NoError
)

sealed class DeviceScreenErrorState() {
    object NoError: DeviceScreenErrorState()
    class Unknown(val message: String): DeviceScreenErrorState()
}