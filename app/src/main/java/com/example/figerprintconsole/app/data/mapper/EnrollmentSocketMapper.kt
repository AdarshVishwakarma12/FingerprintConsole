package com.example.figerprintconsole.app.data.mapper

import com.example.figerprintconsole.app.domain.model.EnrollmentSocketEvent
import com.example.figerprintconsole.app.domain.model.EnrollmentStatus
import com.example.figerprintconsole.app.domain.model.User
import org.json.JSONObject

fun String.toWebsocketEvent(): EnrollmentSocketEvent {
    val root = JSONObject(this)
    val type = root.getString("type")
    val user = root.optJSONObject("user")
    val fingerprintId = root.optInt("fingerprintId")
    val userJson = user?.getJSONObject("user")
    val attendance = root.optJSONObject("attendance")

    return when (type) {

        "ENROLL_PENDING" -> { EnrollmentSocketEvent.EnrollPending(fingerprintId = fingerprintId) }

        "ENROLL_SUCCESS" -> {
            val user = User(
                employeeCode = userJson?.getString("employeeId") ?: "",
                fullName = userJson?.getString("name") ?: "",
                email = userJson?.getString("email"),
                phone = userJson?.getString("phone"),
                department = userJson?.getString("department"),
                notes = userJson?.getString("notes"),
                isActive = userJson?.getBoolean("isActive") ?: false,
                enrolledAt = userJson?.getLong("enrolledAt")?.fromLongToDateString() ?: "Unknown"
            )

            EnrollmentSocketEvent.EnrollSuccess(user = user)
        }

        "ATTENDANCE_EVENT" -> {

            val user = User(
                employeeCode = userJson?.getString("employeeId") ?: "",
                fullName = userJson?.getString("name") ?: "",
                email = userJson?.getString("email"),
                phone = userJson?.getString("phone"),
                department = userJson?.getString("department"),
                notes = userJson?.getString("notes"),
                isActive = userJson?.getBoolean("isActive") ?: false,
                enrolledAt = userJson?.getLong("enrolledAt")?.fromLongToDateString() ?: "Unknown"
            )

            EnrollmentSocketEvent.AttendanceEvent(
                attendanceId = attendance?.optString("_id") ?: "",
                fingerprintId = attendance?.optInt("fingerprintId") ?: -1,
                deviceId = attendance?.optString("deviceId") ?: "",
                action = attendance?.optString("action") ?: "",
                status = attendance?.optString("status") ?: "",
                user = user,
                utcTime = attendance?.optString("utcTime") ?: "",
                customTime = attendance?.optString("customTime") ?: ""
            )
        }

        "ERROR" -> EnrollmentSocketEvent.Error(message = root.getString("message"))

        else -> EnrollmentSocketEvent.Error(message = "Unknown socket event type: $type")
    }
}