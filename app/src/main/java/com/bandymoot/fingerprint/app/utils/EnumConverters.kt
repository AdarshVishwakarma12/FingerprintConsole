package com.bandymoot.fingerprint.app.utils

import androidx.room.TypeConverter
import com.bandymoot.fingerprint.app.data.local.entity.ActionType
import com.bandymoot.fingerprint.app.data.local.entity.AttendanceStatus
import com.bandymoot.fingerprint.app.data.local.entity.AttendanceType
import com.bandymoot.fingerprint.app.data.local.entity.AuthResult
import com.bandymoot.fingerprint.app.data.local.entity.AuthenticationPurpose
import com.bandymoot.fingerprint.app.data.local.entity.DeviceStatusEntityEnum
import com.bandymoot.fingerprint.app.data.local.entity.FromEntity
import com.bandymoot.fingerprint.app.data.local.entity.HandType

class EnumConverters {

    // ---- DeviceStatusEntityEnum ----
    @TypeConverter
    fun fromDeviceStatus(value: DeviceStatusEntityEnum): String =
        value.name

    @TypeConverter
    fun toDeviceStatus(value: String): DeviceStatusEntityEnum =
        DeviceStatusEntityEnum.valueOf(value)

    // ---- HandType ----
    @TypeConverter
    fun fromHandType(value: HandType): String =
        value.name

    @TypeConverter
    fun toHandType(value: String): HandType =
        HandType.valueOf(value)

    // ---- AuthResult ----
    @TypeConverter
    fun fromAuthResult(value: AuthResult): String =
        value.name

    @TypeConverter
    fun toAuthResult(value: String): AuthResult =
        AuthResult.valueOf(value)

    // ---- AuthenticationPurpose ----
    @TypeConverter
    fun fromAuthenticationPurpose(value: AuthenticationPurpose): String =
        value.name

    @TypeConverter
    fun toAuthenticationPurpose(value: String): AuthenticationPurpose =
        AuthenticationPurpose.valueOf(value)

    // ---- ActionType ----
    @TypeConverter
    fun fromActionType(value: ActionType): String =
        value.name

    @TypeConverter
    fun toActionType(value: String): ActionType =
        ActionType.valueOf(value)

    // ---- FromEntity ----
    @TypeConverter
    fun fromFromEntity(value: FromEntity): String =
        value.name

    @TypeConverter
    fun toFromEntity(value: String): FromEntity =
        FromEntity.valueOf(value)

    @TypeConverter
    fun fromAttendanceStatus(value: AttendanceStatus): String =
        value.name

    fun fromAttendanceType(value: String): AttendanceType =
        AttendanceType.valueOf(value)
}