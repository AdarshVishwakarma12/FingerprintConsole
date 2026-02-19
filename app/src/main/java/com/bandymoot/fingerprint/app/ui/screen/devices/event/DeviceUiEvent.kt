package com.bandymoot.fingerprint.app.ui.screen.devices.event

sealed class DeviceUiEvent {
    // Global Screen Events
    object OnPullToRefresh : DeviceUiEvent()
    data class OnDeviceClick(val deviceServerId: String) : DeviceUiEvent()

    // Dialog Management
    object OnDismissDialog : DeviceUiEvent()

    // Stage Transitions (Moving between Details and Delete)
    object OnDeleteClick : DeviceUiEvent()
    object OnDeleteCancel : DeviceUiEvent()

    // Final Destructive Action
    data class OnConfirmDelete(val deviceServerId: String) : DeviceUiEvent()
}