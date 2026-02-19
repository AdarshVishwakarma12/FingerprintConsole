package com.bandymoot.fingerprint.app.ui.screen.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.repository.DeviceRepository
import com.bandymoot.fingerprint.app.ui.screen.devices.event.DeviceUiEvent
import com.bandymoot.fingerprint.app.ui.screen.devices.state.DeviceDialogStage
import com.bandymoot.fingerprint.app.ui.screen.devices.state.DeviceDialogStatus
import com.bandymoot.fingerprint.app.ui.screen.devices.state.DeviceDialogUiState
import com.bandymoot.fingerprint.app.ui.screen.devices.state.DeviceScreenErrorState
import com.bandymoot.fingerprint.app.ui.screen.devices.state.UiStateDeviceScreen
import com.bandymoot.fingerprint.app.utils.showSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceScreenViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,

): ViewModel() {

    private val showDeviceDetailJob: Job? = null
    private val _uiStateDeviceScreen: MutableStateFlow<UiStateDeviceScreen> = MutableStateFlow(UiStateDeviceScreen())
    val uiStateDeviceScreen: StateFlow<UiStateDeviceScreen> = _uiStateDeviceScreen

    init {
        observeDeviceList()
    }

    fun onEvent(event: DeviceUiEvent) {
        when(event) {
            is DeviceUiEvent.OnPullToRefresh -> {
                _uiStateDeviceScreen.update { it.copy(isRefreshing = true) }
                syncFromRemote()
            }
            is DeviceUiEvent.OnDeviceClick -> {
                _uiStateDeviceScreen.update { it.copy(deviceAlertDialog = DeviceDialogUiState.Visible(status = DeviceDialogStatus.Loading)) }
                displayDeviceDetail(event.deviceServerId) // Find and update + Add TimeOut! (if needed) {error friendly code!, can crash the application!. Thank you.)
            }
            is DeviceUiEvent.OnDeleteClick -> {
                _uiStateDeviceScreen.update { currentState ->
                    val dialogState = currentState.deviceAlertDialog as? DeviceDialogUiState.Visible
                    if(dialogState != null) { currentState.copy(deviceAlertDialog = dialogState.copy(stage = DeviceDialogStage.DeleteConfirmation)) }
                    else { currentState }
                }
            }
            is DeviceUiEvent.OnDismissDialog -> {
                _uiStateDeviceScreen.update { it.copy(deviceAlertDialog = DeviceDialogUiState.Hidden) }
            }
            is DeviceUiEvent.OnDeleteCancel -> {
                _uiStateDeviceScreen.update { currentState ->
                    val dialogState = currentState.deviceAlertDialog as? DeviceDialogUiState.Visible
                    if(dialogState != null) { currentState.copy(deviceAlertDialog = dialogState.copy(stage = DeviceDialogStage.Details)) }
                    else { currentState }
                }
            }
            is DeviceUiEvent.OnConfirmDelete -> {
                // callDeleteApi()
                // syncFromRemote()
            }
        }
    }

    fun observeDeviceList() {
        deviceRepository.observeAll()
            .onStart {
                _uiStateDeviceScreen.update {
                    it.copy(
                        isLoading = true
                    )
                }
            }
            .onEach { deviceList ->
                _uiStateDeviceScreen.update {
                    it.copy(
                        isLoading = false,
                        deviceList = deviceList
                    )
                }
            }
            .catch { e ->
                _uiStateDeviceScreen.update {
                    it.copy(
                        isErrorState = true,
                        errorType = DeviceScreenErrorState.Unknown(e.message ?: "Unknown Error")
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun syncFromRemote() {
        viewModelScope.launch {
            val response = deviceRepository.sync()
            when(response) {
                is RepositoryResult.Failed -> {
                    val responseThrowable = response.throwable
                    showSnackBar(responseThrowable.message ?: responseThrowable.localizedMessage)
                    _uiStateDeviceScreen.update { it.copy(isRefreshing = false) }
                }
                is RepositoryResult.Success -> {
                    _uiStateDeviceScreen.update { it.copy(isRefreshing = false) }
                }
            }
        }
    }

    fun displayDeviceDetail(deviceServerId: String) {
        try {
            val deviceData = _uiStateDeviceScreen.value.deviceList.first { it.serverDeviceId == deviceServerId }

            _uiStateDeviceScreen.update { data ->
                data.copy(
                    deviceAlertDialog = DeviceDialogUiState.Visible(
                        stage = DeviceDialogStage.Details,
                        status = DeviceDialogStatus.Success(device = deviceData)
                    )
                )
            }
        } catch (e: Exception) {
            _uiStateDeviceScreen.update { data ->
                data.copy(
                    deviceAlertDialog = DeviceDialogUiState.Visible(
                        status = DeviceDialogStatus.Error(error = e.message ?: e.localizedMessage)
                    ),
                )
            }
        }
    }
}