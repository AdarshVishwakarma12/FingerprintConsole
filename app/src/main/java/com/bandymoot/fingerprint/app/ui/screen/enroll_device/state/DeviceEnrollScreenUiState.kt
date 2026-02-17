package com.bandymoot.fingerprint.app.ui.screen.enroll_device.state

data class DeviceEnrollScreenUiState (
    val deviceName: DeviceEnrollScreenInputState = DeviceEnrollScreenInputState(),
    val deviceCode: DeviceEnrollScreenInputState = DeviceEnrollScreenInputState(),
    val deviceSecret: DeviceEnrollScreenInputState = DeviceEnrollScreenInputState(),
    var isLoading: Boolean = false,
    var error: String? = null
)

data class DeviceEnrollScreenInputState(
    var value: String = "",
    var isValid: Boolean = false
)