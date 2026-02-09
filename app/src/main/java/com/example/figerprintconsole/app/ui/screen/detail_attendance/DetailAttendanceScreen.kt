package com.example.figerprintconsole.app.ui.screen.detail_attendance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.figerprintconsole.app.ui.screen.detail_attendance.components.AttendanceCalendarGrid
import com.example.figerprintconsole.app.ui.screen.detail_attendance.components.DeviceLogsSection
import com.example.figerprintconsole.app.ui.screen.detail_attendance.components.MonthHeader
import com.example.figerprintconsole.app.ui.screen.detail_attendance.components.UserHeader
import com.example.figerprintconsole.app.ui.screen.detail_attendance.components.WeekDayHeader
import com.example.figerprintconsole.app.ui.screen.detail_attendance.event.AttendanceScreenUiEvent
import com.example.figerprintconsole.app.ui.screen.detail_attendance.state.AttendanceBottomSheetState
import com.example.figerprintconsole.app.ui.screen.detail_attendance.state.AttendanceRecordsUiState
import com.example.figerprintconsole.app.ui.screen.detail_attendance.state.MonthCalendarUiState
import com.example.figerprintconsole.app.ui.screen.month_picker.components.MonthPickerBottomSheet

@Composable
fun DetailAttendanceScreen(
    modifier: Modifier = Modifier,
    detailAttendanceViewModel: DetailAttendanceViewModel = hiltViewModel()
) {
    val uiState by detailAttendanceViewModel.detailAttendanceUiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        UserHeader(uiState.userState)

        Spacer(Modifier.height(12.dp))

        MonthHeader(
            date = uiState.selectedYearMonth,
            onChangeMonthClick = { detailAttendanceViewModel.onEvent(AttendanceScreenUiEvent.OpenAttendanceSelectionBottomSheet) }
        )

        Spacer(Modifier.height(18.dp))

        WeekDayHeader()

        Spacer(Modifier.height(32.dp))

        // We need to show the Calendar to the user either way -> attendanceRecord can be on any state!
        // AttendanceRecord: Loading::SHIMMER; ERROR: SnackBar&&GrayCalendar; Success: Data!Duh;
        when(val monthCalendarState = uiState.monthCalendarState) {
            is MonthCalendarUiState.Loading -> { }
            is MonthCalendarUiState.Success -> {
                when (val attendanceRecord = uiState.attendanceRecordsState) {
                    is AttendanceRecordsUiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is AttendanceRecordsUiState.Error -> {
                        Text(
                            text = attendanceRecord.message ?: "Something went wrong",
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    is AttendanceRecordsUiState.Success -> {
                        AttendanceCalendarGrid(
                            dates = monthCalendarState.datesInMonth,
                            currentSelectedDate = uiState.selectedDate,
                            emptyCount = monthCalendarState.emptyDays,
                            attendanceMap = attendanceRecord.recordsGroupedByDate,
                            onClickDay = { day -> detailAttendanceViewModel.onEvent(AttendanceScreenUiEvent.SelectDateOnCalendar(day)) }
                        )

                        Spacer(Modifier.height(12.dp))

                        DeviceLogsSection(
                            logs = attendanceRecord.recordsGroupedByDate[uiState.selectedDate.toString()]
                        )

                    }
                }
            }
            is MonthCalendarUiState.Error -> { }
        }
    }

    // Bottom Sheet Data Selection
    when(uiState.bottomSheetState) {
        is AttendanceBottomSheetState.Closed -> { }
        is AttendanceBottomSheetState.Opened -> {
            MonthPickerBottomSheet(
                currentMonth = uiState.selectedYearMonth,
                onMonthSelected = { newYearMonth ->
                    detailAttendanceViewModel.onEvent(AttendanceScreenUiEvent.UpdateAttendanceDate(newYearMonth))
                },
                onDismiss = { detailAttendanceViewModel.onEvent(AttendanceScreenUiEvent.CloseAttendanceSelectionBottomSheet) }
            )
        }
    }
}