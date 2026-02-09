package com.bandymoot.fingerprint.app.domain.model

data class Device (
    val deviceCode: String,
    val name: String?,
    val location: String?,
    val deviceStatus: String,
    val lastSeenAt: String,
    val batteryLevel: Int?,
    val enrolledAt: String,
    val type: DeviceType = DeviceType.TERMINAL,
    val status: DeviceStatusType = DeviceStatusType.OFFLINE,

    // SECRET INFOs
    val secretKey: String,
    val vendor: String,
    val supportedAlgorithm: String,
    val templateVersion: String
)

enum class DeviceStatusEnum { ACTIVE, INACTIVE, MAINTENANCE, OFFLINE }