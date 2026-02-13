package com.bandymoot.fingerprint.app.data.dto

import com.google.gson.annotations.SerializedName

data class ManagerDetailsResponseDto(
    val success: Boolean,
    val message: String,
    val data: ManagerDto
)

data class ManagerDto(

    @SerializedName("_id")
    val id: String,

    @SerializedName("manager_name")
    val managerName: String,

    @SerializedName("manager_email")
    val managerEmail: String,

//    @SerializedName("manager_password")
//    val managerPassword: String,

    @SerializedName("org_id")
    val organization: OrganizationDto,

    val role: String,

    val status: Boolean,

    val createdAt: String,

    val updatedAt: String,

    @SerializedName("__v")
    val version: Int
)

data class OrganizationDto(

    @SerializedName("_id")
    val id: String,

    @SerializedName("org_name")
    val orgName: String,

    @SerializedName("org_email")
    val orgEmail: String,

//    val password: String,

    @SerializedName("license_key")
    val licenseKey: String,

    @SerializedName("total_devices")
    val totalDevices: Int,

    @SerializedName("total_users")
    val totalUsers: Int,

    val role: String,

    val status: Boolean,

    val createdAt: String,

    val updatedAt: String,

    @SerializedName("__v")
    val version: Int
)