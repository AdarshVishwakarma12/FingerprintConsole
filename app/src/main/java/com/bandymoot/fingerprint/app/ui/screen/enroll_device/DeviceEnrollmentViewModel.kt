package com.bandymoot.fingerprint.app.ui.screen.enroll_device

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.domain.model.RepositoryResult
import com.bandymoot.fingerprint.app.domain.usecase.EnrollDeviceUseCase
import com.bandymoot.fingerprint.app.ui.screen.enroll_device.event.DeviceEnrollScreenUiEvent
import com.bandymoot.fingerprint.app.ui.screen.enroll_device.state.DeviceEnrollScreenInputState
import com.bandymoot.fingerprint.app.ui.screen.enroll_device.state.DeviceEnrollScreenUiState
import com.bandymoot.fingerprint.app.utils.showSnackBar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceEnrollmentViewModel @Inject constructor(
    private val enrollDeviceUseCase: EnrollDeviceUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<DeviceEnrollScreenUiState> =
        MutableStateFlow(DeviceEnrollScreenUiState())

    val uiState: StateFlow<DeviceEnrollScreenUiState> get() = _uiState

    fun onEvent(event: DeviceEnrollScreenUiEvent) {
        when(event) {
            is DeviceEnrollScreenUiEvent.DeviceNameChanged -> { _uiState.update { it.copy(deviceName = DeviceEnrollScreenInputState(event.text)) } }
            is DeviceEnrollScreenUiEvent.DeviceCodeChanged -> { _uiState.update { it.copy(deviceCode = DeviceEnrollScreenInputState(event.text)) } }
            is DeviceEnrollScreenUiEvent.DeviceSecretChanged -> { _uiState.update { it.copy(deviceSecret = DeviceEnrollScreenInputState(event.text)) } }
            is DeviceEnrollScreenUiEvent.ClearError -> { _uiState.update { it.copy(error = null) } }
            is DeviceEnrollScreenUiEvent.ClickedSubmitButton -> {
                _uiState.update { it.copy(isLoading = true) }
                onEnroll()
            }
            is DeviceEnrollScreenUiEvent.ValidateDeviceName -> {
                if(!isValidDeviceName(_uiState.value.deviceName.value)) _uiState.update { it.copy(deviceName = DeviceEnrollScreenInputState(value = it.deviceName.value, isValid = false)) }
                else _uiState.update { it.copy(deviceName = DeviceEnrollScreenInputState(value = it.deviceName.value, isValid = true)) }
            }
            is DeviceEnrollScreenUiEvent.ValidateDeviceCode -> {
                if(!isValidInput(_uiState.value.deviceCode.value)) _uiState.update { it.copy(deviceCode = DeviceEnrollScreenInputState(value = it.deviceCode.value, isValid = false)) }
                else _uiState.update { it.copy(deviceCode = DeviceEnrollScreenInputState(value = it.deviceCode.value, isValid = true)) }
            }
            is DeviceEnrollScreenUiEvent.ValidateDeviceSecret -> {
                if(!isValidInput(_uiState.value.deviceSecret.value)) _uiState.update { it.copy(deviceSecret = DeviceEnrollScreenInputState(value = it.deviceSecret.value, isValid = false)) }
                else _uiState.update { it.copy(deviceSecret = DeviceEnrollScreenInputState(value = it.deviceSecret.value, isValid = true)) }
            }
        }
    }

    fun onEnroll() {
        viewModelScope.launch {
            val enrollResponse = enrollDeviceUseCase(
                _uiState.value.deviceName.value.trim(), // The trim one is Important and Intentional here! [ No offence ]
                _uiState.value.deviceCode.value,
                _uiState.value.deviceSecret.value
            )

            if(enrollResponse is RepositoryResult.Failed) {
                val errorMessage = enrollResponse.throwable.message ?: enrollResponse.throwable.localizedMessage
                _uiState.value.error = errorMessage
                showSnackBar(errorMessage)
            } else {
                showSnackBar("Device Enrolled Successfully!")
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    // Utility Function! (defined here??) (yes it is)
    private fun isValidInput(value: String): Boolean {
        if (value.isBlank()) return false
        val regex = Regex("^[A-Za-z0-9]+$") // regex!
        return regex.matches(value)
    }

    private fun isValidDeviceName(value: String): Boolean {
        if(value.isBlank()) return false
        val regex = Regex("^[A-Za-z0-9 ]+$")
        return regex.matches(value)
    }
}