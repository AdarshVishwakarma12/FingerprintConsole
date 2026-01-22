package com.example.figerprintconsole.app.domain.model

data class AuditLog (
    val ipAddress: String,
    val time: String,
    val actionType: String,
    val fromEntity: String,
    val description: String,
    val organization: Organization,
)