package com.bandymoot.fingerprint.app.data.dto

import com.google.gson.annotations.SerializedName

data class FetchUsersResponseDto(
    val success: Boolean,
    val message: String,
    val data: List<UserServerResponseDto>
)

data class UserServerResponseDto(
    val status: String,
    @SerializedName("_id")
    val id: String,

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

    val devices: List<UserDeviceEnrollmentDto>,

    @SerializedName("org_Id")
    val organization: OrganizationServerResponseDto,

    @SerializedName("manager_Id")
    val manager: ManagerServerResponseDto,

    val createdBy: String?,
    val updatedBy: String?,
    val createdAt: String,
    val updatedAt: String
)

data class UserDeviceEnrollmentDto(
    @SerializedName("_id")
    val id: String,

    val device: DeviceServerResponseDto?,

    @SerializedName("enrollment_status")
    val enrollmentStatus: String,

    @SerializedName("enrollment_at")
    val enrollmentAt: String,

    @SerializedName("enrollment_id")
    val enrollmentId: String
)

data class DeviceServerResponseDto(
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

    val createdBy: String?,
    val updatedBy: String?,

    val createdAt: String,
    val updatedAt: String,

    @SerializedName("last_status")
    val lastStatus: String?,

    @SerializedName("device_last_seen")
    val deviceLastSeen: String?
)

data class OrganizationServerResponseDto(
    @SerializedName("_id")
    val id: String,

    @SerializedName("org_name")
    val orgName: String,

    @SerializedName("org_email")
    val orgEmail: String,

    @SerializedName("license_key")
    val licenseKey: String,

    @SerializedName("total_devices")
    val totalDevices: Int,

    @SerializedName("total_users")
    val totalUsers: Int,

    val role: String,
    val status: Boolean,

    val createdAt: String,
    val updatedAt: String
)

data class ManagerServerResponseDto(
    @SerializedName("_id")
    val id: String,

    @SerializedName("manager_name")
    val managerName: String,

    @SerializedName("manager_email")
    val managerEmail: String,

    @SerializedName("org_id")
    val orgId: String,

    val role: String,
    val status: Boolean,

    val createdAt: String,
    val updatedAt: String
)
