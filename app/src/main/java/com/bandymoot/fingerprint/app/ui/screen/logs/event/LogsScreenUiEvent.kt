package com.bandymoot.fingerprint.app.ui.screen.logs.event

import com.bandymoot.fingerprint.app.ui.screen.logs.state.DeviceTag

sealed class LogsScreenUiEvent {
    data class SelectDevice(val device: DeviceTag): LogsScreenUiEvent()
    object ChangeDateNegative: LogsScreenUiEvent()
    object ChangeDatePositive: LogsScreenUiEvent()
}