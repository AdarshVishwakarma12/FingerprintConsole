package com.example.figerprintconsole.app.ui.screen.detail_attendance.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.figerprintconsole.app.domain.model.AttendanceRecord
import java.time.LocalDate


@Composable
fun AttendanceCalendarGrid(
    dates: List<LocalDate>,
    attendanceMap: Map<String, List<AttendanceRecord>>
) {
    val today = LocalDate.now()

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            items = dates,
            key = { it.toString() }
        ) { date ->
            val records = attendanceMap[date.toString()].orEmpty()
            val isToday = date == today

            AttendanceDayCard(
                date = date,
                records = records,
                isToday = isToday,
                onClick = {
                    // navigate to day detail / bottom sheet / dialog
                }
            )
        }
    }
}
