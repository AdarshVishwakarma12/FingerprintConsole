package com.example.figerprintconsole.app.domain.model

data class Organization (
    val name: String,
    val contactEmail: String,
    val licenseKey: String,
    val totalDevices: Int,
    val totalUsers: Int,
    val isActive: Boolean,
    val createdAt: String // date in string! use mapper at repo!
)