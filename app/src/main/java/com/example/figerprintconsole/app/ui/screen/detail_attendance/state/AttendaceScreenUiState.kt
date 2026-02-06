package com.example.figerprintconsole.app.ui.screen.detail_attendance.state

import com.example.figerprintconsole.app.domain.model.AttendanceRecord
import com.example.figerprintconsole.app.ui.screen.users.state.UserDetailUiState
import java.time.LocalDate
import java.time.YearMonth

data class AttendanceScreenUiState (
    val attendanceData: AttendanceDataObject = AttendanceDataObject.Loading,
    val currentLocalDate: LocalDate = LocalDate.now(),
    val currentMonthList: CurrentMonthCalendarStatus = CurrentMonthCalendarStatus.Loading,
    val userDetail:  UserDetailUiState = UserDetailUiState.Loading,
    val attendanceSelectionState: AttendanceSelectionUiState = AttendanceSelectionUiState.ClosedBottomSheet
)

sealed class CurrentMonthCalendarStatus {
    object Loading: CurrentMonthCalendarStatus()
    data class Success(val data: List<LocalDate>): CurrentMonthCalendarStatus()
    object Error: CurrentMonthCalendarStatus()
}

sealed class AttendanceDataObject() {
    object Loading: AttendanceDataObject()
    data class Success(val data: Map<String, List<AttendanceRecord>>): AttendanceDataObject()
    data class Error(val message: String?): AttendanceDataObject()
}

sealed class AttendanceSelectionUiState {
    object OpenedBottomSheet: AttendanceSelectionUiState()
    object ClosedBottomSheet: AttendanceSelectionUiState()
}