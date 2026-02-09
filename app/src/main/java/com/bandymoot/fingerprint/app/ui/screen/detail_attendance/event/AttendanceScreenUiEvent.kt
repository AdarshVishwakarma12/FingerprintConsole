package com.bandymoot.fingerprint.app.ui.screen.detail_attendance.event

import java.time.LocalDate
import java.time.YearMonth


// For changing the dates!
sealed class AttendanceScreenUiEvent {
    data class UpdateAttendanceDate(val newLocaleDate: YearMonth): AttendanceScreenUiEvent()
    object OpenAttendanceSelectionBottomSheet: AttendanceScreenUiEvent()
    object CloseAttendanceSelectionBottomSheet: AttendanceScreenUiEvent()
    data class SelectDateOnCalendar(val currentDateSelected: LocalDate): AttendanceScreenUiEvent()
}