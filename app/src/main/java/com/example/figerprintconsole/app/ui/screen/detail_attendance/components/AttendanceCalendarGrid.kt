package com.example.figerprintconsole.app.ui.screen.detail_attendance.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.figerprintconsole.app.data.local.entity.AttendanceStatus
import com.example.figerprintconsole.app.domain.model.AttendanceRecord
import java.time.LocalDate


@Composable
fun AttendanceCalendarGrid(
    dates: List<LocalDate>,
    currentSelectedDate: LocalDate?,
    emptyCount: Int,
    attendanceMap: Map<String, List<AttendanceRecord>>,
    onClickDay: (LocalDate) -> Unit
) {
    val today = LocalDate.now()

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        items(emptyCount) { id ->
            EmptyAttendanceDayCard()
        }

        items(
            items = dates,
            key = { it.toString() }
        ) { date ->
            val records = attendanceMap[date.toString()].orEmpty()
            val isToday = date == today

            AttendanceDayCard(
                date = date,
                dayOfWeek = date.dayOfWeek.value,
                attendanceRecordStatus = records.firstOrNull()?.status ?: AttendanceStatus.ABSENT,
                isToday = isToday,
                isSelected = date == currentSelectedDate,
                onClick = onClickDay
            )
        }
    }
}
