package com.bandymoot.fingerprint.app.ui.screen.enroll_user.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Badge
import androidx.compose.material.icons.rounded.BusinessCenter
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Sensors
import androidx.compose.material.icons.rounded.SettingsRemote
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bandymoot.fingerprint.R
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentScreenState
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterUserDetail(
    uiState: EnrollmentState,
    onRetry: () -> Unit,
    onComplete: () -> Unit,
    newUser: NewEnrollUser,
    onInputChanged: (NewEnrollUser) -> Unit
) {
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current
    var deviceExpanded by remember { mutableStateOf(false) }

    // Using a more flexible color palette based on your primary color
    val primaryColor = colorResource(R.color.strat_blue)

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(horizontal = 14.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp) // More breathing room
    ) {
        /** Header Section */
        item {
            Column(modifier = Modifier.padding(top = 2.dp)) {
                Text(
                    text = "User Registration",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Provide details to link the biometric data.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        /** Form Section */
        item {
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                tonalElevation = 1.dp,
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                border = BorderStroke(1.dp, primaryColor.copy(alpha = 0.1f))
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Custom helper for icons to keep code clean
                    FormTextField(
                        value = newUser.name,
                        onValueChange = { onInputChanged(newUser.copy(name = it)) },
                        label = "Full Name",
                        icon = Icons.Rounded.Person
                    )

                    FormTextField(
                        value = newUser.email,
                        onValueChange = { onInputChanged(newUser.copy(email = it)) },
                        label = "Email",
                        icon = Icons.Rounded.Email
                    )

                    FormTextField(
                        value = newUser.employeeId,
                        onValueChange = { onInputChanged(newUser.copy(employeeId = it)) },
                        label = "Employee ID",
                        icon = Icons.Rounded.Badge
                    )

                    FormTextField(
                        value = newUser.phone,
                        onValueChange = { onInputChanged(newUser.copy(name = it)) },
                        label = "Phone",
                        icon = Icons.Rounded.Phone
                    )

                    FormTextField(
                        value = newUser.department,
                        onValueChange = { onInputChanged(newUser.copy(department = it)) },
                        label = "Department",
                        icon = Icons.Rounded.BusinessCenter
                    )

                    /** Improved Device Selector */
                    ExposedDropdownMenuBox(
                        expanded = deviceExpanded,
                        onExpandedChange = { deviceExpanded = !deviceExpanded }
                    ) {
                        OutlinedTextField(
                            value = uiState.listOfDevice
                                .find { it.serverDeviceId == newUser.deviceId }
                                ?.deviceCode ?: "Select Scanner Device",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Scanner Unit") },
                            leadingIcon = {
                                Icon(Icons.Rounded.SettingsRemote, null, tint = primaryColor)
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = deviceExpanded)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            ),
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                                .fillMaxWidth(),
                            shape = MaterialTheme.shapes.large
                        )

                        ExposedDropdownMenu(
                            expanded = deviceExpanded,
                            onDismissRequest = { deviceExpanded = false }
                        ) {
                            uiState.listOfDevice.forEach { device ->
                                DropdownMenuItem(
                                    text = {
                                        Text(device.name ?: device.deviceCode, fontWeight = FontWeight.Medium)
                                    },
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
            }
        }

        /** Action Section */
        item {
            Column(
                modifier = Modifier.padding(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        focusManager.clearFocus()
                        onComplete()
                    },
                    enabled = uiState.enrollmentScreenState is EnrollmentScreenState.UserInfoInput &&
                            newUser.name.isNotBlank(), // Need to add validation here!
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                        disabledContainerColor = Color.LightGray,
                        disabledContentColor = Color.White
                    )
                ) {

                    when (uiState.enrollmentScreenState) {

                        is EnrollmentScreenState.UserInfoInput -> {
                            Text(
                                text = "Next: Prepare Scanner",
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.Rounded.ArrowForward, null)
                        }

                        is EnrollmentScreenState.WaitForDevice -> {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Rounded.Sensors,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Waiting for device...",
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp
                                )
                            }
                        }

                        else -> {
                            Text(
                                text = "Please wait...",
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Show retry only if there was a previous error
                if (uiState.errorMessage != null) {
                    TextButton(
                        onClick = onRetry,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Clear and Reset Form", color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

/** Helper for clean text fields with consistent styling */
@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = colorResource(R.color.strat_blue)) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = colorResource(R.color.strat_blue)
        )
    )
}