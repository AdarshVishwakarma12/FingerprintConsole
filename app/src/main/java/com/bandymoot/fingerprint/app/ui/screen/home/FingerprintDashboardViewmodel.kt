package com.bandymoot.fingerprint.app.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.domain.repository.DeviceRepository
import com.bandymoot.fingerprint.app.ui.screen.home.state.UiHomeDashboardErrorState
import com.bandymoot.fingerprint.app.ui.screen.home.state.UiHomeDashboardState
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
class FingerprintDashboardViewmodel @Inject constructor(
    val deviceRepository: DeviceRepository
): ViewModel() {

    private val _uiStateHomeDashboard: MutableStateFlow<UiHomeDashboardState> = MutableStateFlow(UiHomeDashboardState())
    val uiStateHomeDashboard: StateFlow<UiHomeDashboardState> = _uiStateHomeDashboard

    init {
        observeDevices()
    }

    fun observeDevices() {
        deviceRepository.observeAll()
            .onStart {
                _uiStateHomeDashboard.update {
                    it.copy(
                        isLoading = true,
                        deviceList = emptyList(),
                        isErrorState = false,
                        errorType = UiHomeDashboardErrorState.NoError
                    )
                }
            }
            .onEach {  newDevice ->
                _uiStateHomeDashboard.update {
                    it.copy(
                        isLoading = false,
                        deviceList = newDevice,
                    )
                }
            }
            .catch { e ->
                _uiStateHomeDashboard.update {
                    it.copy(
                        isErrorState = true,
                        errorType = UiHomeDashboardErrorState.Unknown(e.message ?: "Unknown Error")
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}