package com.bandymoot.fingerprint.app.data.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializer

data class LoginResponseDto (
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: String
)