package com.example.figerprintconsole.app.domain.model

data class User(
    val employeeCode: String,
    val fullName: String,
    val email: String?,
    val phone: String?,
    val department: String?,
    val notes: String?,
    val isActive: Boolean,
    val enrolledAt: String,
    val enrollmentStatus: EnrollmentStatus = EnrollmentStatus.ENROLLED,
    val profileImage: String? = null
)