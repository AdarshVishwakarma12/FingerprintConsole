package com.example.figerprintconsole.app.ui.screen.detail_attendance.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmptyAttendanceDayCard() {
    // don't know why but 20.dp feels too static!
    Box(
        modifier = Modifier.size(20.dp)
    ) { }
}