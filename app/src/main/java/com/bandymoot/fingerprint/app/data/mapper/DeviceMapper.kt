package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.data.dto.DeviceDto
import com.bandymoot.fingerprint.app.data.dto.DeviceListResponseDto
import com.bandymoot.fingerprint.app.data.local.entity.DeviceEntity
import com.bandymoot.fingerprint.app.data.local.entity.DeviceStatusEntityEnum
import com.bandymoot.fingerprint.app.domain.model.Device
import java.time.Instant
import kotlin.math.pow

fun DeviceDto.toEntity(): DeviceEntity {
    return DeviceEntity(
        serverDeviceId = id,
        deviceCode = deviceCode,
        secretKey = secretKey,
        name = deviceName,
        location = deviceLocation,
        deviceStatus = lastStatus.toDeviceStatusEntity(),
        lastSeenAt = parseIsoToEpoch(deviceLastSeen ?: "2026-02-16T05:28:40.667Z"), // Update Later!
        firmwareVersion = parseVersionToLong(deviceFirmwareVersion),
        batteryLevel = null, // not provided by API
        enrolledAt = parseIsoToEpoch(createdAt),
        organizationServerId = orgId,
        enrolledByServerManagerId = deviceManagerId,
        vendor = "UNKNOWN", // backend does not send
        supportedAlgorithms = "DEFAULT", // backend does not send
        templateVersion = "1" // safe default
    )
}

fun String.toDeviceStatusEntity(): DeviceStatusEntityEnum =
    when (this.uppercase()) {
        "ONLINE" -> DeviceStatusEntityEnum.ACTIVE
        "OFFLINE" -> DeviceStatusEntityEnum.OFFLINE
        else -> DeviceStatusEntityEnum.MAINTENANCE
    }

fun parseIsoToEpoch(iso: String): Long =
    Instant.parse(iso).toEpochMilli()

fun parseVersionToLong(version: String): Long {
    // "1.0.0" → 100
    return version
        .split(".")
        .mapIndexed { index, part ->
            part.toLong() * 10.0.pow(2 - index).toLong()
        }
        .sum()
}

fun DeviceEntity.toDomain(): Device {
    return Device(
        serverDeviceId = serverDeviceId,
        deviceCode = deviceCode,
        name = name,
        location = location,
        deviceStatus = deviceStatus.toString(),
        lastSeenAt = lastSeenAt.fromLongToDateString(),
        batteryLevel = batteryLevel,
        enrolledAt = enrolledAt.fromLongToDateString(),
        secretKey = secretKey,
        vendor = vendor,
        supportedAlgorithm = supportedAlgorithms,
        templateVersion = templateVersion
    )
}