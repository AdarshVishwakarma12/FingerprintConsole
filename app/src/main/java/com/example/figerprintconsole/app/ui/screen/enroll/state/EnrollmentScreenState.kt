package com.example.figerprintconsole.app.ui.screen.enroll.state

import com.example.figerprintconsole.app.domain.model.NewEnrollUser

sealed class EnrollmentScreenState {
    object Initial
    data class UserInput(val userData: NewEnrollUser): EnrollmentScreenState()
    object FingerprintScan: EnrollmentScreenState()
    object FingerprintScanAgain: EnrollmentScreenState()
    object Completed: EnrollmentScreenState()
    data class Error(val message: String) : EnrollmentScreenState()
}