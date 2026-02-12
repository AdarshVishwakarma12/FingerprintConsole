package com.bandymoot.fingerprint.app.ui.screen.enroll_user.utils

// Helper function for step instructions
object UtilsEnrollScreen {
    fun getStepInstruction(step: Int, isEnrolling: Boolean, isCompleted: Boolean): String {
        return when {
            isCompleted -> "User Enrolled!"
            !isEnrolling -> "Press Start to Enroll New User"
            step == 1 -> "Enter User Information"
            step == 2 -> "Place your finger on the fingerprint sensor"
            step == 3 -> "Lift and place your finger again"
            step == 4 -> "Verify"
            else -> "Error While Enrolling New User"
        }
    }
}