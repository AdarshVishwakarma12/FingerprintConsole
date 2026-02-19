package com.bandymoot.fingerprint.app.ui.screen.devices.state

import com.bandymoot.fingerprint.app.domain.model.Device

data class UiStateDeviceScreen (
    val isLoading: Boolean = true,
    val deviceList: List<Device> = emptyList(),
    val isErrorState: Boolean = false,
    val errorType: DeviceScreenErrorState = DeviceScreenErrorState.NoError,
    val deviceAlertDialog: DeviceDialogUiState = DeviceDialogUiState.Hidden,
    val isRefreshing: Boolean = false
)

sealed class DeviceScreenErrorState() {
    object NoError: DeviceScreenErrorState()
    class Unknown(val message: String): DeviceScreenErrorState()
}

sealed class DeviceDialogUiState {
    object Hidden: DeviceDialogUiState()
    data class Visible(
        val stage: DeviceDialogStage = DeviceDialogStage.Details,
        val status: DeviceDialogStatus = DeviceDialogStatus.Loading
    ): DeviceDialogUiState()
}

sealed class DeviceDialogStage {
    object Details: DeviceDialogStage()
    object DeleteConfirmation: DeviceDialogStage()
}

sealed class DeviceDialogStatus {
    data class Success(val device: Device): DeviceDialogStatus()
    data class Error(val error: String): DeviceDialogStatus()
    object Loading: DeviceDialogStatus()
}