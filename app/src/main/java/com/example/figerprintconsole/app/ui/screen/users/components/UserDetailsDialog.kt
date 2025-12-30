package com.example.figerprintconsole.app.ui.screen.users.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.figerprintconsole.app.domain.model.EnrollmentStatus
import com.example.figerprintconsole.app.domain.model.User
import com.example.figerprintconsole.app.ui.screen.users.utils.UserUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsDialog(
    user: User,
    onDismiss: () -> Unit,
    onEnroll: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            UserAvatar(user = user, size = 48.dp)
        },
        title = {
            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                UserDetailsRow(
                    icon = Icons.Default.Fingerprint,
                    label = "Enrollment Status",
                    value = user.enrollmentStatus.toString().replace('_', ' ')
                )

                UserDetailsRow(
                    icon = Icons.Default.Numbers,
                    label = "Fingerprints",
                    value = user.fingerprintCount.toString()
                )

                UserDetailsRow(
                    icon = Icons.Default.Person,
                    label = "Employee Id",
                    value = user.employeeId.toString()
                )

                user.lastAccess?.let {
                    UserDetailsRow(
                        icon = Icons.Default.AccessTime,
                        label = "Last Access",
                        value = UserUtils.formatDate(it)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        },
        dismissButton = {
            if (user.enrollmentStatus != EnrollmentStatus.ENROLLED) {
                TextButton(onClick = onEnroll) {
                    Text("Enroll Fingerprint")
                }
            }
            TextButton(onClick = onDelete) {
                Text(
                    text = "Delete",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}