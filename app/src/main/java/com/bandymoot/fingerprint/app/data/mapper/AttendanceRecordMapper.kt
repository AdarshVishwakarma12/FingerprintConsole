package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.data.local.projection.AttendanceRecordEntityProjector
import com.bandymoot.fingerprint.app.domain.model.AttendanceRecord
import com.bandymoot.fingerprint.app.utils.AppConstant
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun AttendanceRecordEntityProjector.toDomain(): AttendanceRecord {

    val formatter = DateTimeFormatter.ofPattern("hh:mm a")

    // Calc Check In Time
    var checkIn = "--:--"
    if(attendanceRecordEntity.checkInTime != null) {
        checkIn = formatter.format(Instant.ofEpochMilli(attendanceRecordEntity.checkInTime).atZone(ZoneId.of(AppConstant.ZONE_ID)).toLocalTime())
    }

    var checkOut = "--:--"
    if(attendanceRecordEntity.checkOutTime != null) {
        checkOut = formatter.format(Instant.ofEpochMilli(attendanceRecordEntity.checkOutTime).atZone(ZoneId.of(AppConstant.ZONE_ID)).toLocalTime())
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