package com.example.figerprintconsole.app.ui.enroll.utils

// Helper function for step instructions
object UtilsEnrollScreen {
    fun getStepInstruction(step: Int, isEnrolling: Boolean): String {
        return when {
            isEnrolling -> "Keep your finger on the sensor..."
            step == 1 -> "Place your finger on the fingerprint sensor"
            step == 2 -> "Lift and place your finger again"
            step == 3 -> "Adjust finger position slightly"
            step == 4 -> "One final scan for verification"
            else -> "Follow the on-screen instructions"
        }
    }
}