package com.example.figerprintconsole.app.ui.screen.detail_attendance.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.figerprintconsole.app.data.local.entity.AttendanceStatus
import com.example.figerprintconsole.app.ui.screen.users.utils.UserUtils.contentColorFor
import com.example.figerprintconsole.app.utils.AppConstant
import com.example.figerprintconsole.app.utils.ColorConstant
import java.time.LocalDate

@Composable
fun AttendanceDayCard(
    date: LocalDate,
    dayOfWeek: Int,
    attendanceRecordStatus: AttendanceStatus,
    isToday: Boolean,
    isSelected: Boolean,
    onClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {

    val resolvedStatus = when {
        AppConstant.WEEKENDS_LIST.contains(dayOfWeek) -> AttendanceStatus.WEEKEND
        else -> attendanceRecordStatus
    }

    val backgroundColor = when (resolvedStatus) {
        AttendanceStatus.PRESENT ->
            if (isSelected) ColorConstant.Green35 else ColorConstant.Green30

        AttendanceStatus.ABSENT ->
            if (isSelected) ColorConstant.Red35 else ColorConstant.Red30

        else ->
            if (isSelected) ColorConstant.Gray35 else ColorConstant.Gray30
    }

    val borderColor = when {
        isSelected &&  resolvedStatus == AttendanceStatus.PRESENT -> ColorConstant.Green500
        isSelected &&  resolvedStatus == AttendanceStatus.ABSENT -> ColorConstant.Red500
        isToday -> ColorConstant.Blue60
        resolvedStatus == AttendanceStatus.PRESENT -> ColorConstant.Green60
        resolvedStatus == AttendanceStatus.ABSENT -> ColorConstant.Red60
        else -> ColorConstant.Gray60
    }

    val borderWidth = when {
        isSelected -> 1.dp
        isToday -> 2.dp
        else -> 0.dp
    }

    val shape = RoundedCornerShape(8.dp)

    val textColor = contentColorFor(backgroundColor)

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .border(borderWidth, borderColor, shape)
            .clickable { onClick(date) },
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isToday) FontWeight.ExtraBold else FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}
