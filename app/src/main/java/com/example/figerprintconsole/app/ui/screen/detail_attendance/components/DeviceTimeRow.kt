package com.example.figerprintconsole.app.ui.screen.detail_attendance.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.figerprintconsole.app.domain.model.AttendanceRecord

@Composable
fun DeviceTimeRow(log: AttendanceRecord) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        TimeColumn("IN", log.checkInTime)
        TimeColumn("OUT", log.checkOutTime)
        TimeColumn("TOTAL", log.totalWorkingTime)
    }
}

@Composable
private fun TimeColumn(
    label: String,
    time: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        // modifier = Modifier.weight(1f)
    ) {

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = time.ifBlank { "--" },
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}
