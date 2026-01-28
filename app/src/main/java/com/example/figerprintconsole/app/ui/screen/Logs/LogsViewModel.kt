package com.example.figerprintconsole.app.ui.screen.Logs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.figerprintconsole.app.data.repository.RepositoryResult
import com.example.figerprintconsole.app.domain.repository.DeviceRepository
import com.example.figerprintconsole.app.ui.screen.Logs.event.LogsScreenUiEvent
import com.example.figerprintconsole.app.ui.screen.Logs.state.LogsScreenUiState
import com.example.figerprintconsole.app.ui.screen.Logs.state.SlideDirection
import com.example.figerprintconsole.app.utils.showSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogsViewModel @Inject constructor(
    val deviceRepository: DeviceRepository,
): ViewModel() {

    private val _uiState: MutableStateFlow<LogsScreenUiState> = MutableStateFlow(LogsScreenUiState())
    val uiState: StateFlow<LogsScreenUiState> = _uiState

    init {
        updateDeviceList()
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
}