package com.example.figerprintconsole.app.data.mapper

import androidx.compose.ui.unit.IntRect
import com.example.figerprintconsole.app.data.local.projection.AttendanceRecordEntityProjector
import com.example.figerprintconsole.app.domain.model.AttendanceRecord
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun AttendanceRecordEntityProjector.toDomain(): AttendanceRecord {

    val formatter = DateTimeFormatter.ofPattern("hh:mm a")

    // Calc Check In Time
    var checkIn = "--:--"
    if(attendanceRecordEntity.checkInTime != null) {
        checkIn = formatter.format(Instant.ofEpochMilli(attendanceRecordEntity.checkInTime).atZone(ZoneId.of("Asia/Kolkata")).toLocalTime())
    }

    var checkOut = "--:--"
    if(attendanceRecordEntity.checkOutTime != null) {
        checkOut = formatter.format(Instant.ofEpochMilli(attendanceRecordEntity.checkOutTime).atZone(ZoneId.of("Asia/Kolkata")).toLocalTime())
    }

    return AttendanceRecord(
        userName = user.fullName,
        employeeId = user.userCode,
        dateString = attendanceRecordEntity.date.fromLongToDateString(),
        checkInTime = checkIn,
        checkOutTime = checkOut,
        totalWorkingTime = attendanceRecordEntity.totalWorkingMinutes.toString(),
        breakTime = attendanceRecordEntity.breakMinutes.toString(),
        overtimeTime = attendanceRecordEntity.overtimeMinutes.toString(),
        status = attendanceRecordEntity.status,
        remark = attendanceRecordEntity.remarks
    )
}