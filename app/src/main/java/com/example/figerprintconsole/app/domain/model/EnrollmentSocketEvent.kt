package com.example.figerprintconsole.app.domain.model

sealed class EnrollmentSocketEvent {
    object Connected : EnrollmentSocketEvent()
    object Disconnected : EnrollmentSocketEvent()

    object StartEnrolling : EnrollmentSocketEvent()
    object ScanStepOneCompleted : EnrollmentSocketEvent()
    object ScanStepTwoCompleted : EnrollmentSocketEvent()
    data class VerificationSuccess(
        val attendanceId: String,
        val fingerprintId: Int,
        val deviceId: String,
        val action: String,
        val status: String,
        val user: User,
        val utcTime: String,
        val customTime: String
    ) : EnrollmentSocketEvent()

    data class VerificationFailed(val reason: String) : EnrollmentSocketEvent()
    object CancelEnrolling: EnrollmentSocketEvent()
    data class Error(val message: String) : EnrollmentSocketEvent()
}