package com.bandymoot.fingerprint.app.data.dto

import com.google.gson.annotations.SerializedName

data class UserEnrollRequestDto(
    @SerializedName("user_name")
    val userName: String,

    @SerializedName("user_email")
    val userEmail: String,

    @SerializedName("user_phone")
    val userPhone: String,

    @SerializedName("user_department")
    val userDepartment: String,

    @SerializedName("user_roll")
    val userRoll: String,

    @SerializedName("device_id")
    val deviceId: String
)
