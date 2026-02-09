package com.bandymoot.fingerprint.app.ui.screen.logs.state

import androidx.compose.ui.graphics.Color
import com.bandymoot.fingerprint.app.domain.model.AttendanceRecord
import com.bandymoot.fingerprint.app.domain.model.Device
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class LogsScreenUiState (
    val devices: List<Device> = emptyList(),
    val currentDeviceSelected: DeviceTag = DeviceTag.All,
    val currentUserList: UserListUiStateLogsScreen = UserListUiStateLogsScreen.Loading,
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

sealed class UserListUiStateLogsScreen {
    object Loading: UserListUiStateLogsScreen()
    data class UserList(val data: List<AttendanceRecord>): UserListUiStateLogsScreen()
}

enum class SlideDirection {
    PREVIOUS,
    NEXT,
    NULL
}
