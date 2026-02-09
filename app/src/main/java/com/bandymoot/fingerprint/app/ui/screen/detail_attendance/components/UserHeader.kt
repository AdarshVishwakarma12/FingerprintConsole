package com.bandymoot.fingerprint.app.ui.screen.detail_attendance.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.domain.model.EnrollmentStatus
import com.bandymoot.fingerprint.app.ui.screen.users.components.UserAvatar
import com.bandymoot.fingerprint.app.ui.screen.users.state.UserDetailUiState

@Composable
fun UserHeader(userDetail: UserDetailUiState) {
    if (userDetail is UserDetailUiState.Success) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatar(
                userName = userDetail.user.fullName,
                enrollmentStatus = userDetail.user.enrollmentStatus ?: EnrollmentStatus.PENDING,
                size = 48.dp
            )

            Column {
                Text(
                    text = userDetail.user.fullName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = userDetail.user.userCode,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
