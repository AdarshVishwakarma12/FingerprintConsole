package com.bandymoot.fingerprint.app.ui.screen.enroll.state

import com.bandymoot.fingerprint.app.domain.model.Device
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser

data class EnrollmentState (
    val currentStep: Int = 1,
    val totalSteps: Int = 4,
    val enrollmentProgress: Float = 0f,
    val enrollmentMessage: String = "",
    val isCompleted: Boolean = false,
    val userEnrollInfo: NewEnrollUser = NewEnrollUser(),
    val enrollmentScreenState: EnrollmentScreenState = EnrollmentScreenState.IDLE,
    val isLoading: Boolean = false,
    val isEnrolling: Boolean = false,
    val listOfDevice: List<Device> = emptyList(),
    val errorMessage: String? = null
)

sealed class EnrollmentScreenState {
    object IDLE: EnrollmentScreenState()
    object UserInput: EnrollmentScreenState()
    object EnrollingStepOne: EnrollmentScreenState()
    object EnrollingStepTwo: EnrollmentScreenState()
    object Enrolled: EnrollmentScreenState()
    object Verifying: EnrollmentScreenState()
    object Completed: EnrollmentScreenState()
    data class Error(val message: String?): EnrollmentScreenState()
}