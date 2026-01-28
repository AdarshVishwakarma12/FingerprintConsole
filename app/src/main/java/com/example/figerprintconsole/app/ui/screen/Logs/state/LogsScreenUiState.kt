package com.example.figerprintconsole.app.ui.screen.Logs.state

import androidx.compose.ui.graphics.Color
import com.example.figerprintconsole.app.domain.model.Device
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class LogsScreenUiState (
    val devices: List<Device> = emptyList(),
    val currentDeviceSelected: DeviceTag = DeviceTag.All,
    var currentDate: LocalDate = LocalDate.now(),
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy"),
    val slidingDirection: SlideDirection = SlideDirection.NULL
)

sealed class DeviceTag(
    val color: Color
) {
    object All : DeviceTag(color = Color(0xFF4CAF50))
    data class DeviceItem(val device: Device) : DeviceTag(color = Color(0xFF2196F3))
}

enum class SlideDirection {
    PREVIOUS,
    NEXT,
    NULL
}
