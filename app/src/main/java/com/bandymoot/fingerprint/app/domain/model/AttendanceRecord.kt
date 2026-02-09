package com.bandymoot.fingerprint.app.domain.model

import com.bandymoot.fingerprint.app.data.local.entity.AttendanceStatus

data class AttendanceRecord (
    val userName: String,
    val employeeId: String,
    val dateString: String,
    val checkInTime: String,
    val checkOutTime: String,
    val totalWorkingTime: String,
    val breakTime: String,
    val overtimeTime: String,
    val status: AttendanceStatus,
    val remark: String? = null
)