package com.example.figerprintconsole.app.domain.model

data class Fingerprint (
    // val user: User,
    // val enrolledBy: Manager,
    // val device: Device,
    val fingerIndex: Int,
    val handType: String,
    val qualityScore: Int?,
    val enrolledAt: String,
)