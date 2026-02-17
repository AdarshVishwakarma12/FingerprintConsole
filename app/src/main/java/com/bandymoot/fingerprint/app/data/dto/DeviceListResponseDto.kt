package com.bandymoot.fingerprint.app.data.dto

import com.google.gson.annotations.SerializedName

data class DeviceListResponseDto(
    val success: Boolean,
    val message: String,
    val data: List<DeviceDto>
)

data class DeviceDto(

    @SerializedName("_id")
    val id: String,

    @SerializedName("device_name")
    val deviceName: String,

    @SerializedName("device_code")
    val deviceCode: String,

    @SerializedName("secret_key")
    val secretKey: String,

    @SerializedName("users_count")
    val usersCount: Int,

    @SerializedName("device_location")
    val deviceLocation: String,

    @SerializedName("device_firmware_version")
    val deviceFirmwareVersion: String,

    @SerializedName("device_manager_id")
    val deviceManagerId: String,

    @SerializedName("org_id")
    val orgId: String,

    val status: Boolean,

    val createdBy: String,
    val updatedBy: String?,

    val createdAt: String,
    val updatedAt: String,

    @SerializedName("__v")
    val version: Int,

    @SerializedName("last_status")
    val lastStatus: String,

    @SerializedName("device_last_seen")
    val deviceLastSeen: String?
)
