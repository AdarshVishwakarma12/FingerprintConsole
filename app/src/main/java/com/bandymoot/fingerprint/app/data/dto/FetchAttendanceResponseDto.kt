package com.bandymoot.fingerprint.app.data.dto

import com.google.gson.annotations.SerializedName

data class FetchAttendanceResponseDto(
    val success: Boolean,
    val message: String,
    val data: List<AttendanceDto>
)

data class AttendanceDto(

    @SerializedName("_id")
    val id: String,

    @SerializedName("User_Id")
    val userId: String,

    @SerializedName("Org_Id")
    val orgId: String,

    @SerializedName("Device_Id")
    val deviceId: String,

    @SerializedName("Name")
    val name: String,

    @SerializedName("EMP_Code")
    val empCode: String,

    @SerializedName("DateString")
    val dateString: String,

    @SerializedName("IN_Time")
    val inTime: String?,

    @SerializedName("OUT_Time")
    val outTime: String?,

    @SerializedName("Work_Time")
    val workTime: String?,

    @SerializedName("Over_Time")
    val overTime: String?,

    @SerializedName("Erl_Out")
    val earlyOut: String?,

    @SerializedName("Late_In")
    val lateIn: String?,

    val status: String,

    val createdAt: String,
    val updatedAt: String
)
