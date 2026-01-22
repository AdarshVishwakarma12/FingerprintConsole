package com.example.figerprintconsole.app.domain.model

data class AuthenticationLog (
    val time: String,
    val user: User?,
    val device: Device,
    val result: String,
    val purpose: String,
    val confidenceScore: Int
)