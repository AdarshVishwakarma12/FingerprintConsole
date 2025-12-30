package com.example.figerprintconsole.app.ui.screen.enroll.utils

// Helper function for step instructions
object UtilsEnrollScreen {
    fun getStepInstruction(step: Int, isEnrolling: Boolean): String {
        return when {
            !isEnrolling -> "Press Start to Enroll New User"
            step == 1 -> "Enter User Information"
            step == 2 -> "Place your finger on the fingerprint sensor"
            step == 3 -> "Lift and place your finger again"
            step == 4 -> "Enrolled Success"
            else -> "Error While Enrolling New User"
        }
    }
}