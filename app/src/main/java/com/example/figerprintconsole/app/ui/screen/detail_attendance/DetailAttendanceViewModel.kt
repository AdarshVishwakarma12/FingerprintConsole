package com.example.figerprintconsole.app.ui.screen.detail_attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.figerprintconsole.app.data.repository.RepositoryResult
import com.example.figerprintconsole.app.domain.repository.UserRepository
import com.example.figerprintconsole.app.domain.usecase.GetAttendanceGroupedByDate
import com.example.figerprintconsole.app.ui.screen.detail_attendance.event.AttendanceScreenUiEvent
import com.example.figerprintconsole.app.ui.screen.detail_attendance.state.AttendanceDataObject
import com.example.figerprintconsole.app.ui.screen.detail_attendance.state.AttendanceScreenUiState
import com.example.figerprintconsole.app.ui.screen.detail_attendance.state.AttendanceSelectionUiState
import com.example.figerprintconsole.app.ui.screen.users.state.UserDetailUiState
import com.example.figerprintconsole.app.utils.AppConstant
import com.example.figerprintconsole.app.utils.DebugType
import com.example.figerprintconsole.app.utils.showSnackBar
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
    }

    fun onEvent(event: AttendanceScreenUiEvent) {
        when(event) {
            is AttendanceScreenUiEvent.UpdateAttendanceDate -> {
                _detailAttendanceUiState.update {
                    it.copy(
                        currentLocalDate = event.newLocaleDate
                    )
                }
                collectAttendanceData()
                createMonthList()
            }
            is AttendanceScreenUiEvent.OpenAttendanceSelectionBottomSheet ->  {
                _detailAttendanceUiState.update {
                    it.copy(
                        attendanceSelectionState = AttendanceSelectionUiState.OpenedBottomSheet
                    )
                }
            }
            is AttendanceScreenUiEvent.CloseAttendanceSelectionBottomSheet -> {
                _detailAttendanceUiState.update {
                    it.copy(
                        attendanceSelectionState = AttendanceSelectionUiState.ClosedBottomSheet
                    )
                }
            }
        }
    }

    fun collectUserDetail() {
        viewModelScope.launch {
            val response = userRepository.findDetailUserByServerId(_userCode.value)

            when(response) {
                is RepositoryResult.Success -> {
                    _detailAttendanceUiState.update {
                        it.copy(
                            userDetail = UserDetailUiState.Success(response.data)
                        )
                    }
                }
                is RepositoryResult.Failed -> {
                    showSnackBar("Unable to Load User Details")
                }
            }
        }
    }

    fun collectAttendanceData() {

        viewModelScope.launch {
            val response = getAttendanceGroupedByDate(
                userServerId = _userCode.value,
                date = _detailAttendanceUiState.value.currentLocalDate
            )

            when(response) {
                is RepositoryResult.Success -> {
                    AppConstant.debugMessage("I am syncing attendance data in VIEWMODEL SUCCESS: ${response.data}", "CHECK_FLOW", DebugType.DESCRIPTION)

                    _detailAttendanceUiState.update {
                        it.copy(
                            attendanceData = AttendanceDataObject.Success(data = response.data)
                        )
                    }

                    // Update the Month List!
                    createMonthList()
                }
                is RepositoryResult.Failed -> {
                    AppConstant.debugMessage("I am syncing attendance data in VIEWMODEL ERROR: ${response.throwable.message}", "CHECK_FLOW", DebugType.DESCRIPTION)

                    showSnackBar(response.throwable.localizedMessage ?: "Unknown Error Occurred!")
                    _detailAttendanceUiState.update {
                        it.copy(
                            attendanceData = AttendanceDataObject.Error(response.throwable.message)
                        )
                    }
                }
            }
        }
    }

    // This is just a util function
    // And not specific to ViewModel!
    fun createMonthList() {

        val localDate = _detailAttendanceUiState.value.currentLocalDate

        val daysList = (1..localDate.lengthOfMonth()).map { day ->
            LocalDate.of(localDate.year, localDate.month, day)
        }

        _detailAttendanceUiState.update {
            it.copy(
                currentMonthList = daysList
            )
        }
    }
}