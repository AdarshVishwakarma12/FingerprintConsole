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
import com.example.figerprintconsole.app.ui.screen.detail_attendance.components.MonthHeader
import com.example.figerprintconsole.app.ui.screen.detail_attendance.components.UserHeader
import com.example.figerprintconsole.app.ui.screen.detail_attendance.components.WeekDayHeader
import com.example.figerprintconsole.app.ui.screen.detail_attendance.event.AttendanceScreenUiEvent
import com.example.figerprintconsole.app.ui.screen.detail_attendance.state.AttendanceDataObject
import com.example.figerprintconsole.app.ui.screen.detail_attendance.state.AttendanceSelectionUiState
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

        UserHeader(uiState.userDetail)

        Spacer(Modifier.height(12.dp))

        MonthHeader(
            date = uiState.currentLocalDate,
            onChangeMonthClick = { detailAttendanceViewModel.onEvent(AttendanceScreenUiEvent.OpenAttendanceSelectionBottomSheet) }
        )

        Spacer(Modifier.height(18.dp))

        WeekDayHeader()

        Spacer(Modifier.height(12.dp))

        when (val attendance = uiState.attendanceData) {
            is AttendanceDataObject.Loading -> {
                CircularProgressIndicator()
            }

            is AttendanceDataObject.Error -> {
                Text(
                    text = attendance.message ?: "Something went wrong",
                    color = MaterialTheme.colorScheme.error
                )
            }

            is AttendanceDataObject.Success -> {
                AttendanceCalendarGrid(
                    dates = uiState.currentMonthList,
                    attendanceMap = attendance.data
                )
            }
        }
    }

    when(uiState.attendanceSelectionState) {
        is AttendanceSelectionUiState.ClosedBottomSheet -> { }
        is AttendanceSelectionUiState.OpenedBottomSheet -> {
            MonthPickerBottomSheet(
                currentMonthInLocalDate = uiState.currentLocalDate,
                onMonthSelected = { localDate ->
                    detailAttendanceViewModel.onEvent(AttendanceScreenUiEvent.UpdateAttendanceDate(localDate))
                },
                onDismiss = { detailAttendanceViewModel.onEvent(AttendanceScreenUiEvent.CloseAttendanceSelectionBottomSheet) }
            )
        }
    }
}