package com.example.figerprintconsole.app.ui.screen.detail_attendance.state

import com.example.figerprintconsole.app.domain.model.AttendanceRecord
import com.example.figerprintconsole.app.ui.screen.users.state.UserDetailUiState
import java.time.LocalDate
import java.time.YearMonth

data class AttendanceScreenUiState(
    val attendanceRecordsState: AttendanceRecordsUiState = AttendanceRecordsUiState.Loading,
    val todayDate: LocalDate = LocalDate.now(),
    val selectedYearMonth: YearMonth = YearMonth.now(),
    val selectedDate: LocalDate? = null,
    val monthCalendarState: MonthCalendarUiState = MonthCalendarUiState.Loading,
    val userState: UserDetailUiState = UserDetailUiState.Loading,
    val bottomSheetState: AttendanceBottomSheetState =
        AttendanceBottomSheetState.Closed
)

// Stores the days in list! for the current month selected!
// For Loading Status -> you can add Shimmer! (optional for now)
sealed class MonthCalendarUiState {
    object Loading : MonthCalendarUiState()
    data class Success(
        val datesInMonth: List<LocalDate>,
        val emptyDays: Int
    ) : MonthCalendarUiState()
    object Error : MonthCalendarUiState()
}

// This will hold the List of Attendance Record!
sealed class AttendanceRecordsUiState {
    object Loading : AttendanceRecordsUiState()
    data class Success(
        val recordsGroupedByDate: Map<String, List<AttendanceRecord>>
    ) : AttendanceRecordsUiState()
    data class Error(
        val message: String?
    ) : AttendanceRecordsUiState()
}


// Used while opening / closing of bottom sheet / dialog box
sealed class AttendanceBottomSheetState {
    object Opened : AttendanceBottomSheetState()
    object Closed : AttendanceBottomSheetState()
}