package com.bandymoot.fingerprint.app.ui.screen.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.repository.DeviceRepository
import com.bandymoot.fingerprint.app.ui.screen.devices.event.UiEventDeviceEvent
import com.bandymoot.fingerprint.app.ui.screen.devices.state.DeviceScreenErrorState
import com.bandymoot.fingerprint.app.ui.screen.devices.state.UiStateDeviceScreen
import com.bandymoot.fingerprint.app.utils.showSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _uiStateDeviceScreen: MutableStateFlow<UiStateDeviceScreen> = MutableStateFlow(UiStateDeviceScreen())
    val uiStateDeviceScreen: StateFlow<UiStateDeviceScreen> = _uiStateDeviceScreen

    init {
        observeDeviceList()
    }

    fun onEvent(event: UiEventDeviceEvent) {
        when(event) {
            is UiEventDeviceEvent.PullToRefresh -> {
                _uiStateDeviceScreen.update { it.copy(isRefreshing = true) }

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
}