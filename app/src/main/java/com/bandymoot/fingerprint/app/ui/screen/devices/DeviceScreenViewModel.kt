package com.bandymoot.fingerprint.app.ui.screen.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.domain.repository.DeviceRepository
import com.bandymoot.fingerprint.app.ui.screen.devices.state.DeviceScreenErrorState
import com.bandymoot.fingerprint.app.ui.screen.devices.state.UiStateDeviceScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DeviceScreenViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository
): ViewModel() {

    private val _uiStateDeviceScreen: MutableStateFlow<UiStateDeviceScreen> = MutableStateFlow(UiStateDeviceScreen())
    val uiStateDeviceScreen: StateFlow<UiStateDeviceScreen> = _uiStateDeviceScreen

    init {
        observeDeviceList()
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