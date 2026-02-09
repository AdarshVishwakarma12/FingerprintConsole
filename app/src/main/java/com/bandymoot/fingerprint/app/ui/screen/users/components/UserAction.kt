package com.bandymoot.fingerprint.app.ui.screen.users.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.domain.model.EnrollmentStatus

@Composable
fun UserActions(
    enrollmentStatus: EnrollmentStatus,
    onEnroll: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        IconButton(
            onClick = onEnroll,
            enabled = enrollmentStatus != EnrollmentStatus.ENROLLED,
            modifier = Modifier.weight(weight = .5f).border(1.dp, Color.White, RoundedCornerShape(15.dp)),
        ) {
            Icon(
                imageVector = Icons.Default.Fingerprint,
                contentDescription = "Enroll fingerprint",
                tint = if (enrollmentStatus != EnrollmentStatus.ENROLLED)
                    MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }

        Spacer(Modifier.padding(4.dp))

        IconButton(
            onClick = onDelete,
            modifier = Modifier.weight(weight = .5f).border(1.dp, Color.White, RoundedCornerShape(15.dp)),
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete user",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}