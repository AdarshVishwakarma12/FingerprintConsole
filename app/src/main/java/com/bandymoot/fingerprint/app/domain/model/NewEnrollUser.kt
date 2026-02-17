package com.bandymoot.fingerprint.app.domain.model

data class NewEnrollUser(
    val name: String = "Afnan Khan",
    val email: String = "afnan@gmail.com",
    val employeeId: String = "110220260232",
    val phone: String = "0000000000",
    val department: String = "Data Analytics",
    val deviceId: String = "",
    val timeZone: String = "UTC",
    val isValid: Boolean = false
)