package com.bandymoot.fingerprint.app.data.dto

import com.google.gson.annotations.SerializedName

data class EnrollNewDeviceRequest (
    @SerializedName("device_name") val deviceName: String,
    @SerializedName("device_code") val deviceCode: String,
    @SerializedName("secret_key") val secretKey: String
)