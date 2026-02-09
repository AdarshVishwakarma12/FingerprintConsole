package com.bandymoot.fingerprint.app.ui.screen.detail_attendance.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.domain.model.AttendanceRecord

@Composable
fun DeviceLogsSection(
    logs: List<AttendanceRecord>?,
    modifier: Modifier = Modifier
) {

    when {
        logs.isNullOrEmpty() -> {
            EmptyDeviceLogs()
        }

        else -> {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = modifier.fillMaxWidth()
            ) {
                logs.forEach { log ->
                    DeviceLogItem(log)
                }
            }
        }
    }
}
