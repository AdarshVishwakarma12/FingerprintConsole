package com.example.figerprintconsole.app.ui.screen.Logs.event

import com.example.figerprintconsole.app.ui.screen.Logs.state.DeviceTag

sealed class LogsScreenUiEvent {
    data class SelectDevice(val device: DeviceTag): LogsScreenUiEvent()
    object ChangeDateNegative: LogsScreenUiEvent()
    object ChangeDatePositive: LogsScreenUiEvent()
}