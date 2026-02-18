package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.data.dto.DeviceDto
import com.bandymoot.fingerprint.app.data.local.entity.DeviceEntity
import com.bandymoot.fingerprint.app.data.local.entity.DeviceStatusEntityEnum
import com.bandymoot.fingerprint.app.domain.model.Device
import java.time.Instant
import kotlin.math.pow

fun DeviceDto.toEntity(): DeviceEntity? {
    return try {
        DeviceEntity(
            serverDeviceId = id,
            deviceCode = deviceCode,
            secretKey = secretKey,
            name = deviceName,
            location = deviceLocation,
            deviceStatus = lastStatus?.toDeviceStatusEntity() ?: DeviceStatusEntityEnum.OFFLINE,
            lastSeenAt = deviceLastSeen?.let { parseIsoToEpoch(it) } ?: 0L,
            firmwareVersion = deviceFirmwareVersion?.let { parseVersionToLong(it) } ?: 0L,
            batteryLevel = null, // not provided by API
            enrolledAt = createdAt?.let { parseIsoToEpoch(it) } ?: 0L,
            organizationServerId = orgId,
            enrolledByServerManagerId = deviceManagerId,
            vendor = "UNKNOWN", // backend does not send
            supportedAlgorithms = "DEFAULT", // backend does not send
            templateVersion = "1" // safe default
        )
    } catch (_: Exception) {
        null
    }
}

fun String.toDeviceStatusEntity(): DeviceStatusEntityEnum =
    when (this.uppercase()) {
        "ONLINE" -> DeviceStatusEntityEnum.ACTIVE
        "OFFLINE" -> DeviceStatusEntityEnum.OFFLINE
        "MAINTENANCE" -> DeviceStatusEntityEnum.MAINTENANCE
        else -> DeviceStatusEntityEnum.OFFLINE
    }

fun parseIsoToEpoch(iso: String): Long {
    return try {
        Instant.parse(iso).toEpochMilli()
    } catch (_: Exception) {
        0L
    }
}

fun parseVersionToLong(version: String): Long {
    // "1.0.0" → 100
    return try {
        version
            .split(".")
            .mapIndexed { index, part ->
                part.toLong() * 10.0.pow(2 - index).toLong()
            }
            .sum()
    } catch (_: Exception) {
        0L
    }
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