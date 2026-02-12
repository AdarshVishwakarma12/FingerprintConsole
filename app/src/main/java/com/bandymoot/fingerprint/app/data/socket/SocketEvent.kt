package com.bandymoot.fingerprint.app.data.socket

sealed class SocketEvent {

    object IDLE: SocketEvent()

    data class Device(
        val data: SocketTopicDevice
    ): SocketEvent()

    data class Attendance(
        val data: SocketTopicAttendance
    ): SocketEvent()

    object Connected : SocketEvent()

    object Disconnected: SocketEvent()

    data class Error(val message: String): SocketEvent()
}

data class SocketTopicDevice (
    val deviceId: String,
    val status: SocketTopicDeviceStatus
)

data class SocketTopicAttendance(
    val name: String,
    val status: SocketTopicAttendanceStatus
)

enum class SocketTopicDeviceStatus { OFFLINE, ONLINE }

enum class SocketTopicAttendanceStatus { CHECK_IN, CHECK_OUT }