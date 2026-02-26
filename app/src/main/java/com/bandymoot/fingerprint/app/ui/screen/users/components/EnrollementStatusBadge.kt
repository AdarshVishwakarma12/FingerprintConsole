package com.bandymoot.fingerprint.app.ui.screen.users.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.domain.model.EnrollmentStatus

@Composable
fun EnrollmentStatusBadge(
    status: EnrollmentStatus,
) {
    val (text, color, icon) = when (status) {
        EnrollmentStatus.ENROLLED -> Triple(
            "Enrolled",
            MaterialTheme.colorScheme.primaryContainer,
            Icons.Default.CheckCircle
        )
        EnrollmentStatus.PENDING -> Triple(
            "Pending",
            MaterialTheme.colorScheme.tertiaryContainer,
            Icons.Default.Pending
        )
        EnrollmentStatus.NOT_ENROLLED -> Triple(
            "Not Enrolled",
            MaterialTheme.colorScheme.errorContainer,
            Icons.Default.Warning
        )
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(10.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}