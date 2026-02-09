package com.bandymoot.fingerprint.app.ui.screen.enroll.state

sealed class EnrollmentScreenState {
        object IDLE: EnrollmentScreenState()
        object UserInput: EnrollmentScreenState()
        object EnrollingStepOne: EnrollmentScreenState()
        object EnrollingStepTwo: EnrollmentScreenState()
        object Verifying: EnrollmentScreenState()
        object Completed: EnrollmentScreenState()
        data class Error(val message: String?): EnrollmentScreenState()
}