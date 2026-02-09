package com.bandymoot.fingerprint.app.domain.model

data class NewEnrollUser(
    val name: String = "",
    val email: String = "",
    val employeeId: String = "",
    val phone: String = "",
    val department: String = "",
    val timeZone: String = "UTC",
    val isValid: Boolean = false
)