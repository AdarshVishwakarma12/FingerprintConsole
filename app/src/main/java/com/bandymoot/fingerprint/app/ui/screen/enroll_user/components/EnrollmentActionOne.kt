package com.bandymoot.fingerprint.app.ui.screen.enroll_user.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnrollmentActionOne(
    uiState: EnrollmentState,
    onRetry: () -> Unit,
    onComplete: () -> Unit,
    newUser: NewEnrollUser,
    onInputChanged: (NewEnrollUser) -> Unit
) {
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    var deviceExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .imePadding()
    ) {

        item {
            OutlinedTextField(
                value = newUser.name,
                onValueChange = { onInputChanged(newUser.copy(name = it)) },
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = newUser.email,
                onValueChange = { onInputChanged(newUser.copy(email = it)) },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = newUser.employeeId,
                onValueChange = { onInputChanged(newUser.copy(employeeId = it)) },
                label = { Text("Employee ID") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = newUser.phone,
                onValueChange = { onInputChanged(newUser.copy(phone = it)) },
                label = { Text("Phone") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = newUser.department,
                onValueChange = { onInputChanged(newUser.copy(department = it)) },
                label = { Text("Department") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        /** Device selector */
        item {
            ExposedDropdownMenuBox(
                expanded = deviceExpanded,
                onExpandedChange = { deviceExpanded = !deviceExpanded }
            ) {
                OutlinedTextField(
                    value = uiState.listOfDevice.find { device -> device.serverDeviceId == newUser.deviceId}?.deviceCode ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Device") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = deviceExpanded)
                    },
                    modifier = Modifier
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = deviceExpanded,
                    onDismissRequest = { deviceExpanded = false }
                ) {
                    uiState.listOfDevice.forEach { device ->
                        DropdownMenuItem(
                            text = { Text(device.name ?: device.deviceCode) },
                            onClick = {
                                onInputChanged(newUser.copy(deviceId = device.serverDeviceId))
                                deviceExpanded = false
                                focusManager.clearFocus()
                            }
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        onComplete()
                    },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.large,
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            text = "Continue",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                if (uiState.errorMessage != null) {
                    OutlinedButton(
                        onClick = onRetry,
                        modifier = Modifier.weight(1f),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}