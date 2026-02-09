package com.bandymoot.fingerprint.app.ui.screen.users.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.domain.model.EnrollmentStatus
import com.bandymoot.fingerprint.app.domain.model.UserDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsDialog(
    userDetail: UserDetail,
    onDismiss: () -> Unit,
    onEnroll: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            UserAvatar(
                userName = userDetail.fullName,
                enrollmentStatus = userDetail.enrollmentStatus ?: EnrollmentStatus.NOT_ENROLLED,
                size = 48.dp
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
            Column {
                Text(
                    text = userDetail.email ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                UserDetailsRow(
                    icon = Icons.Default.Fingerprint,
                    label = "Enrollment Status",
                    value = userDetail.enrollmentStatus.toString().replace('_', ' ')
                )

                UserDetailsRow(
                    icon = Icons.Default.Numbers,
                    label = "Fingerprints",
                    value = userDetail.department.toString()
                )

                UserDetailsRow(
                    icon = Icons.Default.Person,
                    label = "Employee Id",
                    value = userDetail.userCode
                )

                UserDetailsRow(
                    icon = Icons.Default.AccessTime,
                    label = "Last Access",
                    value = userDetail.enrolledAt
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Dismiss")
            }
        },
        dismissButton = {
            if (userDetail.enrollmentStatus != EnrollmentStatus.ENROLLED) {
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