package com.bandymoot.fingerprint.app.data.socket

sealed class SocketEvent {

    object IDLE: SocketEvent()

    data class Device(
        val data: SocketTopicDevice
    ): SocketEvent()

    data class Attendance(
        val data: SocketTopicAttendance
    ): SocketEvent()

    data class EnrollProgress(
        val data: SocketTopicEnroll
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

data class SocketTopicEnroll(
    val deviceId: String,
    val step: SocketEnrollmentStep,
    val message: String
)

sealed class SocketEnrollmentStep {
    object Start: SocketEnrollmentStep()
    object CheckDuplicateFinger: SocketEnrollmentStep()
    object FirstScan: SocketEnrollmentStep()
    object SecondScan: SocketEnrollmentStep()
    object Success: SocketEnrollmentStep()
    object Failed: SocketEnrollmentStep()
    object UndefinedStep: SocketEnrollmentStep()
}

enum class SocketTopicDeviceStatus { OFFLINE, ONLINE }

enum class SocketTopicAttendanceStatus { CHECK_IN, CHECK_OUT }