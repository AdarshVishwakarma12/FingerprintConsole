package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.data.socket.SocketEnrollmentStep
import com.bandymoot.fingerprint.app.data.socket.SocketTopicAttendance
import com.bandymoot.fingerprint.app.data.socket.SocketTopicAttendanceStatus
import com.bandymoot.fingerprint.app.data.socket.SocketTopicDevice
import com.bandymoot.fingerprint.app.data.socket.SocketTopicDeviceStatus
import com.bandymoot.fingerprint.app.data.socket.SocketTopicEnroll
import org.json.JSONObject

// TOPIC::DEVICE
fun JSONObject.toSocketTopicDevice(): SocketTopicDevice {
    val deviceStatus = this.getString("status").toSocketTopicDeviceStatus() ?: throw Exception("Invalid Device Status")
    return SocketTopicDevice(
        deviceId = this.getString("deviceId") ?: throw Exception("Invalid Device Id"),
        status = deviceStatus
    )
}

fun String.toSocketTopicDeviceStatus(): SocketTopicDeviceStatus? {
    return if(this == "ONLINE") {
        SocketTopicDeviceStatus.ONLINE
    }
    else if(this == "OFFLINE") {
        SocketTopicDeviceStatus.OFFLINE
    } else null
}

// TOPIC::ATTENDANCE
fun JSONObject.toSocketTopicAttendance(): SocketTopicAttendance {
    val attendanceStatus = this.getString("status").toSocketTopicAttendanceStatus() ?: throw Exception("Invalid Attendance Status")

    return SocketTopicAttendance(
        name = this.getString("name") ?: throw Exception("Invalid Attendance Data"),
        status = attendanceStatus
    )
}

fun String.toSocketTopicAttendanceStatus(): SocketTopicAttendanceStatus? {
    return if(this == "Check IN") {
        SocketTopicAttendanceStatus.CHECK_IN
    } else if(this == "Check OUT") {
        SocketTopicAttendanceStatus.CHECK_OUT
    } else null
}

// TOPIC::EnrollProgress
fun JSONObject.toSocketTopicEnrollProgress(): SocketTopicEnroll {
    return SocketTopicEnroll(
        deviceId = this.getString("deviceId") ?: throw Exception("Device Id not found"), // This is not handled gracefully! Which is bad!.
        step = this.optString("step", "undefined step").toSocketEnrollmentSteps(),
        message = this.getString("message") ?: "Enrollment in Progress"
    )
}

// This String is Straight From FC Backend!! -> Hence it's more Static!
fun String.toSocketEnrollmentSteps(): SocketEnrollmentStep {
    return when(this) {
        "start" -> SocketEnrollmentStep.Start
        "checking" -> SocketEnrollmentStep.CheckDuplicateFinger
        "first_scan" -> SocketEnrollmentStep.FirstScan
        "second_scan" -> SocketEnrollmentStep.SecondScan
        "success" -> SocketEnrollmentStep.Success
        "failed" -> SocketEnrollmentStep.Failed
        else -> SocketEnrollmentStep.UndefinedStep
    }
}