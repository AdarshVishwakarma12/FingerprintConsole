package com.bandymoot.fingerprint.app.ui.screen.detail_attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.repository.UserRepository
import com.bandymoot.fingerprint.app.domain.usecase.GetAttendanceGroupedByDate
import com.bandymoot.fingerprint.app.ui.screen.detail_attendance.event.AttendanceScreenUiEvent
import com.bandymoot.fingerprint.app.ui.screen.detail_attendance.state.AttendanceBottomSheetState
import com.bandymoot.fingerprint.app.ui.screen.detail_attendance.state.AttendanceRecordsUiState
import com.bandymoot.fingerprint.app.ui.screen.detail_attendance.state.AttendanceScreenUiState
import com.bandymoot.fingerprint.app.ui.screen.detail_attendance.state.MonthCalendarUiState
import com.bandymoot.fingerprint.app.ui.screen.users.state.UserDetailUiState
import com.bandymoot.fingerprint.app.utils.AppConstant
import com.bandymoot.fingerprint.app.utils.showSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DetailAttendanceViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val getAttendanceGroupedByDate: GetAttendanceGroupedByDate
): ViewModel() {

    private val _userCode: MutableStateFlow<String> = MutableStateFlow("userId")

    private val _detailAttendanceUiState: MutableStateFlow<AttendanceScreenUiState> =
        MutableStateFlow(AttendanceScreenUiState())

    val detailAttendanceUiState: StateFlow<AttendanceScreenUiState> = _detailAttendanceUiState

    // Should only be access once! while navigating!
    fun saveUserCode(userId: String) {
        _userCode.update { userId }
        collectAttendanceData()
        collectUserDetail()
        updateMonthCalendarState()
    }

    fun onEvent(event: AttendanceScreenUiEvent) {
        when(event) {
            is AttendanceScreenUiEvent.UpdateAttendanceDate -> {
                // We need to trigger all of these at once! BRUH!
                _detailAttendanceUiState.update { it.copy(selectedYearMonth = event.newLocaleDate) }
                collectAttendanceData()
                updateMonthCalendarState()
            }
            is AttendanceScreenUiEvent.OpenAttendanceSelectionBottomSheet ->  {
                _detailAttendanceUiState.update { it.copy(bottomSheetState = AttendanceBottomSheetState.Opened) }
            }
            is AttendanceScreenUiEvent.CloseAttendanceSelectionBottomSheet -> {
                _detailAttendanceUiState.update { it.copy(bottomSheetState = AttendanceBottomSheetState.Closed) }
            }
            is AttendanceScreenUiEvent.SelectDateOnCalendar -> {
                _detailAttendanceUiState.update { it.copy(selectedDate = event.currentDateSelected) }
            }
        }
    }

    // User Detail is One time call per Screen!
    fun collectUserDetail() {
        viewModelScope.launch {
            val response = userRepository.findDetailUserByServerId(_userCode.value)

            when(response) {
                is RepositoryResult.Success -> {
                    _detailAttendanceUiState.update { it.copy(userState = UserDetailUiState.Success(response.data)) }
                }
                is RepositoryResult.Failed -> {
                    showSnackBar("Unable to Load User Details")
                }
            }
        }
    }

    // This function updates the Attendance Record
    fun collectAttendanceData() {

        viewModelScope.launch {
            val response = getAttendanceGroupedByDate(
                userServerId = _userCode.value,
                currentYearMonth = _detailAttendanceUiState.value.selectedYearMonth
            )

            when(response) {
                is RepositoryResult.Success -> {
                    // Update the Month List!?? HERE?? There can be case when we have Failed response! and we still have to show the calendar with error state! So, No.
                    // Update the Attendance Record
                    _detailAttendanceUiState.update { it.copy(attendanceRecordsState = AttendanceRecordsUiState.Success(recordsGroupedByDate = response.data)) }
                }
                is RepositoryResult.Failed -> {
                    showSnackBar(response.throwable.localizedMessage ?: "Unknown Error Occurred!")
                    _detailAttendanceUiState.update { it.copy(attendanceRecordsState = AttendanceRecordsUiState.Error(response.throwable.message)) }
                }
            }
        }
    }

    // This is just a util function
    // And not specific to ViewModel!
    fun updateMonthCalendarState() {

        // Update the State to Loading!!
        _detailAttendanceUiState.update { it.copy(monthCalendarState = MonthCalendarUiState.Loading) }

        // Get the Today Date and extract the month data
        val selectedYearMonth = _detailAttendanceUiState.value.selectedYearMonth

        AppConstant.debugMessage("LocalDate: $selectedYearMonth")
        // 1::Monday 7::Sunday
        // We are starting month with MONDAY && MONDAY=1 !
        // Explicitly set the start date as 1 to calculate the dayOfWeek!
        val emptyListLength = LocalDate.of(selectedYearMonth.year, selectedYearMonth.month, 1).dayOfWeek.value - 1 // Specific!
        val daysList = (1..selectedYearMonth.lengthOfMonth()).map { day -> LocalDate.of(selectedYearMonth.year, selectedYearMonth.month, day) }

        _detailAttendanceUiState.update { it.copy(monthCalendarState = MonthCalendarUiState.Success(datesInMonth = daysList, emptyDays = emptyListLength)) }

        AppConstant.debugMessage("List:: ${_detailAttendanceUiState.value.monthCalendarState}")
    }
}