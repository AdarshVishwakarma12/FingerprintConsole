package com.example.figerprintconsole.app.data.mapper

import com.example.figerprintconsole.app.domain.model.EnrollmentSocketEvent
import com.example.figerprintconsole.app.domain.model.EnrollmentStatus
import com.example.figerprintconsole.app.domain.model.User
import org.json.JSONObject

fun String.toWebsocketEvent(): EnrollmentSocketEvent {
    val root = JSONObject(this)
    val type = root.getString("type")

    return when (type) {

        "ATTENDANCE_EVENT" -> mapAttendanceEvent(root)

        "" -> EnrollmentSocketEvent.StartEnrolling
        // "" -> EnrollmentSocketEvent.ScanStepOneCompleted

        "ERROR" -> EnrollmentSocketEvent.Error(
            message = root.getString("message")
        )

        else -> EnrollmentSocketEvent.Error(
            message = "Unknown socket event type: $type"
        )
    }
}

fun mapAttendanceEvent(root: JSONObject): EnrollmentSocketEvent.VerificationSuccess {
    val attendance = root.getJSONObject("attendance")
    val userJson = attendance.getJSONObject("user")

    val user = User(
        id = userJson.getString("_id"),
        fingerprintId = userJson.getInt("fingerprintId"),
        employeeId = userJson.getString("employeeId"),
        name = userJson.getString("name"),
        email = userJson.getString("email"),
        enrollmentStatus = EnrollmentStatus.ENROLLED, // This is a direct value!! !!WARNING!!
    )

    return EnrollmentSocketEvent.VerificationSuccess(
        attendanceId = attendance.getString("_id"),
        fingerprintId = attendance.getInt("fingerprintId"),
        deviceId = attendance.getString("deviceId"),
        action = attendance.getString("action"),
        status = attendance.getString("status"),
        user = user,
        utcTime = attendance.getString("utcTime"),
        customTime = attendance.getString("customTime")
    )
}