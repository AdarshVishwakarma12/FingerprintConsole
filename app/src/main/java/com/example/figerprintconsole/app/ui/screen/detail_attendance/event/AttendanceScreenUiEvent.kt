package com.example.figerprintconsole.app.ui.screen.detail_attendance.event

import java.time.LocalDate


// For changing the dates!
sealed class AttendanceScreenUiEvent {
    data class UpdateAttendanceDate(val newLocaleDate: LocalDate): AttendanceScreenUiEvent()
    object OpenAttendanceSelectionBottomSheet: AttendanceScreenUiEvent()
    object CloseAttendanceSelectionBottomSheet: AttendanceScreenUiEvent()
}