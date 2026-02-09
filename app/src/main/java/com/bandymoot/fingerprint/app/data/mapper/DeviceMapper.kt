package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.data.local.entity.DeviceEntity
import com.bandymoot.fingerprint.app.domain.model.Device

fun DeviceEntity.toDomain(): Device {
    return Device(
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