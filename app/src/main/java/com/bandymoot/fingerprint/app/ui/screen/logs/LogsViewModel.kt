package com.bandymoot.fingerprint.app.ui.screen.logs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.repository.AttendanceRepository
import com.bandymoot.fingerprint.app.domain.repository.DeviceRepository
import com.bandymoot.fingerprint.app.ui.screen.logs.event.LogsScreenUiEvent
import com.bandymoot.fingerprint.app.ui.screen.logs.state.LogsScreenUiState
import com.bandymoot.fingerprint.app.ui.screen.logs.state.SlideDirection
import com.bandymoot.fingerprint.app.ui.screen.logs.state.UserListUiStateLogsScreen
import com.bandymoot.fingerprint.app.utils.AppConstant
import com.bandymoot.fingerprint.app.utils.DebugType
import com.bandymoot.fingerprint.app.utils.showSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class LogsViewModel @Inject constructor(
    val deviceRepository: DeviceRepository,
    val attendanceRecordRepository: AttendanceRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<LogsScreenUiState> = MutableStateFlow(LogsScreenUiState())
    val uiState: StateFlow<LogsScreenUiState> = _uiState

    init {
        updateDeviceList()
        getAllAttendanceForToday(_uiState.value.currentDate)
    }

    fun onEvent(event: LogsScreenUiEvent) {
        when(event) {
            is LogsScreenUiEvent.SelectDevice -> {
                _uiState.update {
                    it.copy(
                        currentDeviceSelected = event.device
                    )
                }
            }
            is LogsScreenUiEvent.ChangeDateNegative -> {
                val currentDate = _uiState.value.currentDate
                val newDate = currentDate.minusDays(1)
                _uiState.update {
                    it.copy(
                        currentDate = newDate,
                        slidingDirection = SlideDirection.PREVIOUS
                    )
                }
                getAllAttendanceForToday(date = newDate)
            }
            is LogsScreenUiEvent.ChangeDatePositive -> {
                val currentDate = _uiState.value.currentDate
                val newDate = currentDate.plusDays(1)
                _uiState.update {
                    it.copy(
                        currentDate = newDate,
                        slidingDirection = SlideDirection.NEXT
                    )
                }
                getAllAttendanceForToday(date = newDate)
            }
        }
    }

    fun updateDeviceList() {
        viewModelScope.launch {
            val deviceList = deviceRepository.observeDeviceByCurrentManager()
            when(deviceList) {
                is RepositoryResult.Failed -> {
                    showSnackBar(deviceList.throwable.message ?: "Something went wrong while loading Devices")
                }
                is RepositoryResult.Success -> {
                    _uiState.update { it.copy(devices = deviceList.data) }
                }
            }

        }
    }

    fun getAllAttendanceForToday(date: LocalDate) {

        // Show Loading Status
        _uiState.update {
            it.copy(
                currentUserList = UserListUiStateLogsScreen.Loading
            )
        }

        viewModelScope.launch {
            try {
                val attendanceData = attendanceRecordRepository.getAttendanceByDate(date)
                when(attendanceData) {
                    is RepositoryResult.Success -> {

                        delay(1500) // 1.5 Second delay!

                        _uiState.update {
                            it.copy(
                                currentUserList = UserListUiStateLogsScreen.UserList(data = attendanceData.data)
                            )
                        }

                        AppConstant.debugMessage("debugMessage: " + attendanceData.data, debugType = DebugType.DESCRIPTION)
                    }
                    is RepositoryResult.Failed -> {
                        showSnackBar(attendanceData.throwable.message ?: "Error Occurred While Fetching Data")
                        AppConstant.debugMessage("debugMessage: " + attendanceData.throwable.message, debugType = DebugType.ERROR)
                    }
                }
            } catch (e: Exception) {
                showSnackBar(e.message ?: "Oops! Something Went Wrong!")
            }
        }
    }
}