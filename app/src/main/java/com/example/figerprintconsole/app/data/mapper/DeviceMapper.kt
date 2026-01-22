package com.example.figerprintconsole.app.data.mapper

import com.example.figerprintconsole.app.data.local.entity.DeviceEntity
import com.example.figerprintconsole.app.domain.model.Device

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