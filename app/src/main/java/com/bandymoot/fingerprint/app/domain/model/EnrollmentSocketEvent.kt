package com.bandymoot.fingerprint.app.domain.model

sealed class EnrollmentSocketEvent {
    object IDLE : EnrollmentSocketEvent()
    object Connected : EnrollmentSocketEvent()
    object Disconnected : EnrollmentSocketEvent()

    data class EnrollPending(val fingerprintId: Int) : EnrollmentSocketEvent()

    data class EnrollSuccess(val user: User) : EnrollmentSocketEvent()
    // Received: {"type":"ENROLL_SUCCESS","user":{"fingerprintId":8,"employeeId":"1005","name":"Afnan1","email":"test@mail2.com","phone":"9999999999","department":"IT","customTimezone":"+05:30","lastDeviceIn":null,"currentStatus":"OUT","_id":"6954fcdb2964c2c6a214f26a","createdAt":"2025-12-31T10:37:15.621Z","updatedAt":"2025-12-31T10:37:15.621Z","__v":0}}

    data class AttendanceEvent(
        val attendanceId: String,
        val fingerprintId: Int,
        val deviceId: String,
        val action: String,
        val status: String,
        val user: User,
        val utcTime: String,
        val customTime: String
    ) : EnrollmentSocketEvent()
    // {"_id":"6954fd162964c2c6a214f27b","fingerprintId":8,"deviceId":"251221000002","action":"IN","status":"GRANTED","user":{"_id":"6954fcdb2964c2c6a214f26a","fingerprintId":8,"employeeId":"1005","name":"Afnan1","email":"test@mail2.com","phone":"9999999999","department":"IT","customTimezone":"+05:30","lastDeviceIn":null,"currentStatus":"OUT","createdAt":"2025-12-31T10:37:15.621Z","updatedAt":"2025-12-31T10:37:15.621Z","__v":0},"sessions":{"251221000002":"IN"},"utcTime":"2025-12-31T10:38:14.441Z","customTime":"2025-12-31T16:08:14.441Z","createdAt":"2025-12-31T10:38:14.442Z"}

    data class VerificationFailed(val reason: String) : EnrollmentSocketEvent()

    data class Error(val message: String) : EnrollmentSocketEvent()
}