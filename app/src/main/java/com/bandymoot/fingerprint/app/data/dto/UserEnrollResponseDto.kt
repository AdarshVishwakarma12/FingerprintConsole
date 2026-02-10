package com.bandymoot.fingerprint.app.data.dto

import com.google.gson.annotations.SerializedName

data class UserEnrollResponseDto(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("userId")
    val userId: String
)
