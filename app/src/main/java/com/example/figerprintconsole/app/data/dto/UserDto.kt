package com.example.figerprintconsole.app.data.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("_id")
    val id: String,

    val fingerprintId: Int,

    val employeeId: String,

    val name: String,

    val email: String?,

    val phone: String?,

    val department: String?,

    val customTimezone: String?,

    val lastDeviceIn: String?, // You can parse to OffsetDateTime if needed

    val currentStatus: String,

    val createdAt: String, // Or OffsetDateTime

    val updatedAt: String, // Or OffsetDateTime

    @SerializedName("__v")
    val version: Int
)