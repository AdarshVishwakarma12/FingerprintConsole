package com.bandymoot.fingerprint.app.ui.screen.user_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.bandymoot.fingerprint.app.domain.model.Device
import com.bandymoot.fingerprint.app.domain.model.EnrollmentStatus
import com.bandymoot.fingerprint.app.domain.model.UserDetail
import com.bandymoot.fingerprint.app.ui.screen.users.components.UserAvatar
import com.bandymoot.fingerprint.app.ui.screen.users.components.UserDetailsRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsDialog(
    userDetail: UserDetail,
    onDismiss: () -> Unit,
    onEnroll: () -> Unit,
    onDeleteUser: (UserDetail?) -> Unit = { },
    navigateToAttendance: (UserDetail) -> Unit = { }
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        onDismissRequest = onDismiss,
        icon = {
            UserAvatar(
                userName = userDetail.fullName,
                enrollmentStatus = userDetail.enrollmentStatus ?: EnrollmentStatus.NOT_ENROLLED,
                size = 64.dp // slightly bigger
            )
        },
        title = {
            Text(
                text = userDetail.fullName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            // scrollable content
            Column(
                Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp) // bigger dialog
            ) {
                Column(
                    modifier = Modifier
//                        .fillMaxWidth()
//                        .heightIn(max = 500.dp) // bigger dialog
                        .verticalScroll(rememberScrollState())
                ) {
                    UserDetailsRow(Icons.Default.Email, "Email", userDetail.email ?: "-")
                    UserDetailsRow(Icons.Default.Person, "Employee ID", userDetail.userCode)
                    UserDetailsRow(
                        Icons.Default.Fingerprint,
                        "Enrollment Status",
                        userDetail.enrollmentStatus.toString().replace('_', ' ')
                    )
                    UserDetailsRow(Icons.Default.AccessTime, "Last Access", userDetail.enrolledAt)
                    UserDetailsRow(Icons.Default.Work, "Department", userDetail.department ?: "-")
                    UserDetailsRow(Icons.Default.Phone, "Phone", userDetail.phone)
                    UserDetailsRow(Icons.Default.LocationOn, "Notes", userDetail.notes ?: "-")

                    Spacer(modifier = Modifier.height(16.dp))

                    // Enrolled By
                    userDetail.enrolledBy?.let { manager ->
                        Text("Enrolled By", fontWeight = FontWeight.Bold)
                        UserDetailsRow(Icons.Default.Person, "Full Name", manager.fullName)
                        UserDetailsRow(Icons.Default.Email, "Email", manager.email)
                        UserDetailsRow(Icons.Default.Person, "Username", manager.userName)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Devices
                if (userDetail.devices.isNotEmpty()) {
                    Text("Devices", fontWeight = FontWeight.Bold)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 200.dp) // max height to prevent overflow
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp)
                    ) {
                        LazyColumn {
                            items(userDetail.devices) { item ->
                                DeviceRow(device = item)
                            }
                        }
                    }

                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        },
        dismissButton = {
            TextButton(onClick = { navigateToAttendance(userDetail) }) {
                Text("Attendance Data")
            }

        }
    )
}

@Composable
fun DeviceRow(device: Device) {
    UserDetailsRow(
        icon = Icons.Default.Fingerprint,
        label = "${device.name} (${device.deviceCode})",
        value = ""
    )
}
