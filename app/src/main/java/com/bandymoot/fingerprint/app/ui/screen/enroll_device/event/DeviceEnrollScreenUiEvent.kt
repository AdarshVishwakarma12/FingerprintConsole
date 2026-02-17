package com.bandymoot.fingerprint.app.ui.screen.enroll_device.event

sealed class DeviceEnrollScreenUiEvent {
    data class DeviceNameChanged(val text: String): DeviceEnrollScreenUiEvent()
    object ValidateDeviceName: DeviceEnrollScreenUiEvent()

    data class DeviceCodeChanged(val text: String): DeviceEnrollScreenUiEvent()
    object ValidateDeviceCode: DeviceEnrollScreenUiEvent()
    data class DeviceSecretChanged(val text: String): DeviceEnrollScreenUiEvent()
    object ValidateDeviceSecret: DeviceEnrollScreenUiEvent()
    object ClickedSubmitButton: DeviceEnrollScreenUiEvent()
    object ClearError : DeviceEnrollScreenUiEvent()
}