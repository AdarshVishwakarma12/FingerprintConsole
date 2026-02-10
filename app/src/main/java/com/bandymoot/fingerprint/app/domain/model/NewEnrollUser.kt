package com.bandymoot.fingerprint.app.domain.model

data class NewEnrollUser(
    val name: String = "",
    val email: String = "",
    val employeeId: String = "",
    val phone: String = "",
    val department: String = "",
    val deviceId: String = "6982c9e3aa8bf4f172a1dbdc",
    val timeZone: String = "UTC",
    val isValid: Boolean = false
)