package com.example.figerprintconsole.app.domain.model

data class UserDetail (
    val userCode: String,
    val fullName: String,
    val email: String?,
    val phone: String,
    val department: String?,
    val notes: String?,
    val isActive: Boolean,
    val enrolledAt: String,
    val organization: Organization,
    val enrolledBy: Manager?,
    val devices: List<Device>,
    val fingerprint: List<Fingerprint>,
    val enrollmentStatus: EnrollmentStatus?,
)