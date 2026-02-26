package com.bandymoot.fingerprint.app.ui.screen.enroll_device

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bandymoot.fingerprint.app.ui.screen.auth.components.AuthErrorBanner
import com.bandymoot.fingerprint.app.ui.screen.enroll_device.component.CustomActionButton
import com.bandymoot.fingerprint.app.ui.screen.enroll_device.component.CustomField
import com.bandymoot.fingerprint.app.ui.screen.enroll_device.event.DeviceEnrollScreenUiEvent
import com.bandymoot.fingerprint.app.utils.authColors

@Composable
fun DeviceEnrollScreen(
    deviceEnrollmentViewModel: DeviceEnrollmentViewModel = hiltViewModel()
) {

    val focusManager = LocalFocusManager.current

    val uiState by deviceEnrollmentViewModel.uiState.collectAsState()

    // Initial is important! only if the dev define the value before hand, in domain.
    LaunchedEffect(Unit) {
        deviceEnrollmentViewModel.onEvent(DeviceEnrollScreenUiEvent.ValidateDeviceName)
        deviceEnrollmentViewModel.onEvent(DeviceEnrollScreenUiEvent.ValidateDeviceCode)
        deviceEnrollmentViewModel.onEvent(DeviceEnrollScreenUiEvent.ValidateDeviceSecret)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
                deviceEnrollmentViewModel.onEvent(DeviceEnrollScreenUiEvent.ValidateDeviceName)
                deviceEnrollmentViewModel.onEvent(DeviceEnrollScreenUiEvent.ValidateDeviceCode)
                deviceEnrollmentViewModel.onEvent(DeviceEnrollScreenUiEvent.ValidateDeviceSecret)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomField(
            colors = authColors(),
            inputString = uiState.deviceName.value,
            placeholderString = "",
            labelString = "Device Name",
            isError = (!uiState.deviceName.value.isEmpty() && !uiState.deviceName.isValid),
            enabled = true,
            onTextChanged = { input ->
                deviceEnrollmentViewModel.onEvent(DeviceEnrollScreenUiEvent.DeviceNameChanged(input))
                deviceEnrollmentViewModel.onEvent(DeviceEnrollScreenUiEvent.ValidateDeviceName)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomField(
            colors = authColors(),
            inputString = uiState.deviceCode.value,
            placeholderString = "",
            labelString = "Device Code",
            isError = (!uiState.deviceCode.value.isEmpty() && !uiState.deviceCode.isValid),
            enabled = true,
            onTextChanged = { input ->
                deviceEnrollmentViewModel.onEvent(DeviceEnrollScreenUiEvent.DeviceCodeChanged(input))
                deviceEnrollmentViewModel.onEvent(DeviceEnrollScreenUiEvent.ValidateDeviceCode)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomField(
            colors = authColors(),
            inputString = uiState.deviceSecret.value,
            placeholderString = "",
            labelString = "Secret Key",
            isError = (!uiState.deviceSecret.value.isEmpty() && !uiState.deviceSecret.isValid),
            enabled = true,
            onTextChanged = { input ->
                deviceEnrollmentViewModel.onEvent(DeviceEnrollScreenUiEvent.DeviceSecretChanged(input))
                deviceEnrollmentViewModel.onEvent(DeviceEnrollScreenUiEvent.ValidateDeviceSecret)
            }
        )

        if (uiState.error != null) {

            Spacer(modifier = Modifier.height(22.dp))

            // Reused Banner!
            AuthErrorBanner(
                message = uiState.error ?: "No Error",
                colors = authColors(),
                onClearClick = {
                    deviceEnrollmentViewModel.onEvent(DeviceEnrollScreenUiEvent.ClearError)
                },
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        CustomActionButton(
            "Submit",
            authColors(),
            onClick = { deviceEnrollmentViewModel.onEvent(DeviceEnrollScreenUiEvent.ClickedSubmitButton) },
            isValidAll = (
                    uiState.deviceName.isValid &&
                    uiState.deviceCode.isValid &&
                    uiState.deviceSecret.isValid
            ),
            isLoading = uiState.isLoading
        )
    }
}
