package com.example.figerprintconsole.app.ui.screen.enroll.state

data class EnrollmentState (
    val currentStep: Int = 1,
    val totalSteps: Int = 4,
    val isEnrolling: Boolean = false,
    val enrollmentProgress: Float = 0f,
    val enrollmentMessage: String = "",
    val isCompleted: Boolean = false,
    val errorMessage: String? = null
)