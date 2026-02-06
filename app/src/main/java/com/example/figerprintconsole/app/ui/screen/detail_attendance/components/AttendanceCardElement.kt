package com.example.figerprintconsole.app.ui.screen.detail_attendance.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import com.example.figerprintconsole.app.data.local.entity.AttendanceStatus
import com.example.figerprintconsole.app.domain.model.AttendanceRecord

@Preview(showBackground = true)
@Composable
fun AttendanceCardElement(
    text: String = "01",
    attendanceRecord: List<AttendanceRecord> = emptyList(),
    isActive: Boolean = true,
    modifier: Modifier = Modifier
) {

    val status = attendanceRecord.firstOrNull()?.status ?: AttendanceStatus.ABSENT

    // Define colors based on attendance status
    val statusColor = when(status) {
        AttendanceStatus.PRESENT -> Color(0xFF4CAF50) // Green
        AttendanceStatus.ABSENT -> Color(0xFFF44336) // Red
        AttendanceStatus.LATE -> Color(0xFFFFC107) // Amber/Yellow
        AttendanceStatus.HALF_DAY -> Color(0xFF81D4FA) // Light Blue
        AttendanceStatus.LEAVE -> Color(0xFF9E9E9E) // Gray
        AttendanceStatus.HOLIDAY -> Color(0xFF64B5F6) // Blue
        AttendanceStatus.WEEKEND -> Color(0xFFE0E0E0) // Light Gray
        AttendanceStatus.ON_DUTY -> Color(0xFFBA68C8) // Purple
    }
    val shape = RoundedCornerShape(15f)

    Box(
        modifier = modifier
            .size(36.dp)
            .background(
                color = if(text == "00") Color.Transparent else statusColor.copy(alpha = .8f),
                shape = shape
            )
            .then(
                if(isActive && text != "00") {
                    Modifier.border(1.dp, statusColor.copy(alpha = 1f), shape)
                } else Modifier
            ),
            // .clickable()
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = if(text == "00") Color.Transparent else Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
