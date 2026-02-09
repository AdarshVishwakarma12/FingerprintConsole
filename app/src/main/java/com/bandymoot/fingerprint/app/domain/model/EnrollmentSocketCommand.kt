package com.bandymoot.fingerprint.app.domain.model

sealed class EnrollmentSocketCommand {
    object Connect : EnrollmentSocketCommand()

    object Disconnect : EnrollmentSocketCommand()

    object StartEnrolling : EnrollmentSocketCommand()

    object CancelEnrolling : EnrollmentSocketCommand()

    object VerifyFingerprint : EnrollmentSocketCommand()
}