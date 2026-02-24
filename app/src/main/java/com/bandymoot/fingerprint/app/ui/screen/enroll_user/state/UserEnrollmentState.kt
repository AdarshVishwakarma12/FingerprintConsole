package com.bandymoot.fingerprint.app.ui.screen.enroll_user.state

import android.os.Message
import com.bandymoot.fingerprint.app.domain.model.Device
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser

data class EnrollmentState (
    val isCompleted: Boolean = false,
    val userEnrollInfo: NewEnrollUser = NewEnrollUser(),
    val enrollmentSocketState: EnrollmentSocketState = EnrollmentSocketState.DISCONNECTED,
    val enrollmentScreenState: EnrollmentScreenState = EnrollmentScreenState.IDLE,
    val enrollmentErrorState: EnrollmentErrorState? = null,
    val isLoading: Boolean = false,
    val listOfDevice: List<Device> = emptyList(),
    val errorMessage: String? = null
)

data class EnrollmentErrorState(
    val message: String
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
        enrollmentMessage = "Ready to begin enrollment"
    )

    object ConnectingToSocket: EnrollmentScreenState(
        isEnrolling = false,
        currentStep = 1,
        enrollmentProgress = 0.25f,
        enrollmentMessage = "Connecting with Socket"
    )

    object UserInfoInput: EnrollmentScreenState(
        isEnrolling = false,
        currentStep = 1,
        enrollmentProgress = 0.2f,
        enrollmentMessage = "Enter User Information"
    )

    object WaitForDevice: EnrollmentScreenState(
        isEnrolling = false,
        currentStep = 1,
        enrollmentProgress = 0.25f,
        enrollmentMessage = "Waiting for Device to come ONLINE"
    )

    object EnrollmentStarted: EnrollmentScreenState(
        isEnrolling = true,
        currentStep = 2,
        enrollmentProgress = 0.3f,
        enrollmentMessage = "Enrollment is Started"
    )

    object CheckDuplicateFinger : EnrollmentScreenState(
        isEnrolling = false,
        currentStep = 3,
        enrollmentProgress = 0.10f,
        enrollmentMessage = "Place your finger. Analyzing scanner for existing prints..."
    )

    // FirstScan -> {Failed} / {Success + Remove}
    object FirstScan : EnrollmentScreenState(
        isEnrolling = true,
        currentStep = 4,
        enrollmentProgress = 0.25f,
        enrollmentMessage = " Place your finger on the sensor"
    )

    // Second Scan -> {Failed} / {Creating + Storing + Success}
    object SecondScan : EnrollmentScreenState(
        isEnrolling = true,
        currentStep = 5,
        enrollmentProgress = 0.75f,
        enrollmentMessage = "Lift and place the same finger again"
    )

    object Enrolled : EnrollmentScreenState(
        isEnrolling = false,
        currentStep = 6,
        enrollmentProgress = 1f,
        enrollmentMessage = "Fingerprint enrolled successfully"
    )

//    Duplicate Finger / Unsuccess Scan / Other Reason
//    data class Failed(val message: String?) : EnrollmentScreenState(
//        isEnrolling = false,
//        currentStep = 0,
//        enrollmentProgress = 0f,
//        enrollmentMessage = message ?: "Error occurred while enrolling!"
//    )
}

enum class EnrollmentSocketState {
    CONNECTED,
    DISCONNECTED
}