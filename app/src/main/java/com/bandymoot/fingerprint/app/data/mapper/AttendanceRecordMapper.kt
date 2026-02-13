package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.data.dto.AttendanceDto
import com.bandymoot.fingerprint.app.data.local.entity.AttendanceRecordEntity
import com.bandymoot.fingerprint.app.data.local.entity.AttendanceStatus
import com.bandymoot.fingerprint.app.data.local.entity.AttendanceType
import com.bandymoot.fingerprint.app.data.local.projection.AttendanceRecordEntityProjector
import com.bandymoot.fingerprint.app.domain.model.AttendanceRecord
import com.bandymoot.fingerprint.app.utils.AppConstant
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun AttendanceDto.toEntity(): AttendanceRecordEntity {

    val startOfDay = parseDateStartOfDay(dateString)

    return AttendanceRecordEntity(
        serverAttendanceId = id,
        userServerId = userId,
        date = startOfDay,
        month = extractMonth(dateString),
        checkInTime = parseDateTime(dateString, inTime),
        checkOutTime = parseDateTime(dateString, outTime),
        totalWorkingMinutes = parseMinutes(workTime),
        breakMinutes = 0, // Backend not providing
        overtimeMinutes = parseMinutes(overTime),
        status = mapStatus(status),
        attendanceType = AttendanceType.REGULAR, // Backend not specifying
        remarks = null,
        approvedAt = null,
        isModified = false,
        modifiedReason = null,
        modifiedByManagerId = null,
        deviceServerId = deviceId,
        organizationServerId = orgId,
        createdAt = Instant.parse(createdAt).toEpochMilli(),
        updatedAt = Instant.parse(updatedAt).toEpochMilli()
    )
}


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

private fun parseDateStartOfDay(date: String): Long {
    val localDate = LocalDate.parse(date)
    return localDate
        .atStartOfDay(ZoneId.of(AppConstant.ZONE_ID))
        .toInstant()
        .toEpochMilli()
}

private fun extractMonth(date: String): Int {
    return LocalDate.parse(date).monthValue - 1
}

private fun parseDateTime(date: String, time: String?): Long? {
    if (time.isNullOrBlank()) return null

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a")
    val localDateTime = LocalDateTime.parse("$date $time", formatter)

    return localDateTime
        .atZone(ZoneId.of(AppConstant.ZONE_ID))
        .toInstant()
        .toEpochMilli()
}

private fun parseMinutes(time: String?): Int {
    if (time.isNullOrBlank()) return 0

    val parts = time.split(":")
    if (parts.size != 2) return 0

    val hours = parts[0].toIntOrNull() ?: 0
    val minutes = parts[1].toIntOrNull() ?: 0

    return hours * 60 + minutes
}

private fun mapStatus(status: String): AttendanceStatus {
    return when (status.uppercase()) {
        "P" -> AttendanceStatus.PRESENT
        "A" -> AttendanceStatus.ABSENT
        "HD" -> AttendanceStatus.HALF_DAY
        "L" -> AttendanceStatus.LEAVE
        else -> AttendanceStatus.PRESENT
    }
}
