package com.bandymoot.fingerprint.app.domain.model

data class Manager (
    val userName: String,
    val fullName: String,
    val email: String,
    val isSuperior: Boolean,
    val isActive: Boolean,
    val lastLoginAt: String,
    val createdAt: String,
    // val organization: Organization,
    // val devices: List<Device>,
    // val users: List<User>
)