package com.example.figerprintconsole.app.ui.screen.users.state

import java.time.LocalDateTime

data class User(
    val id: String,
    val name: String,
    val email: String,
    val profileImage: String? = null,
    val enrollmentStatus: EnrollmentStatus,
    val lastAccess: LocalDateTime? = null,
    val fingerprintCount: Int = 0
)