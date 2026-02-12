package com.bandymoot.fingerprint.app.ui.screen.enroll_user.state

import com.bandymoot.fingerprint.app.domain.model.Device
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser

data class EnrollmentState (
    val totalSteps: Int = 4,
    val isCompleted: Boolean = false,
    val userEnrollInfo: NewEnrollUser = NewEnrollUser(),
    val enrollmentScreenState: EnrollmentScreenState = EnrollmentScreenState.IDLE,
    val isLoading: Boolean = false,
    val listOfDevice: List<Device> = emptyList(),
    val errorMessage: String? = null
)

sealed class EnrollmentScreenState(
    val isEnrolling: Boolean,
    val currentStep: Int,
    val enrollmentProgress: Float,
    val enrollmentMessage: String
) {

    object IDLE : EnrollmentScreenState(
        isEnrolling = false,
        currentStep = 0,
        enrollmentProgress = 0f,
        enrollmentMessage = "Start Enrollment Process"
    )

    object UserInput : EnrollmentScreenState(
        isEnrolling = false,
        currentStep = 1,
        enrollmentProgress = 0.10f,
        enrollmentMessage = "Enter user details"
    )

    object EnrollingStepOne : EnrollmentScreenState(
        isEnrolling = true,
        currentStep = 2,
        enrollmentProgress = 0.25f,
        enrollmentMessage = "Place finger on scanner"
    )

    object EnrollingStepTwo : EnrollmentScreenState(
        isEnrolling = true,
        currentStep = 3,
        enrollmentProgress = 0.60f,
        enrollmentMessage = "Lift and place finger again"
    )

    object Enrolled : EnrollmentScreenState(
        isEnrolling = false,
        currentStep = 4,
        enrollmentProgress = 0.90f,
        enrollmentMessage = "Enrollment successful"
    )

    object Verifying : EnrollmentScreenState(
        isEnrolling = true,
        currentStep = 4,
        enrollmentProgress = 0.95f,
        enrollmentMessage = "Verifying enrollment"
    )

    object Completed : EnrollmentScreenState(
        isEnrolling = false,
        currentStep = 4,
        enrollmentProgress = 1.00f,
        enrollmentMessage = "Completed"
    )

    data class Error(val message: String?) : EnrollmentScreenState(
        isEnrolling = false,
        currentStep = -1,
        enrollmentProgress = 0f,
        enrollmentMessage = message ?: "Error occurred while enrolling!"
    )
}