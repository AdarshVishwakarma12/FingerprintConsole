package com.bandymoot.fingerprint.app.ui.screen.enroll.event

import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser

sealed class EnrollScreenEvent {
    object StartEnrollment: EnrollScreenEvent()
    object ValidateUserInfoAndStartBiometric: EnrollScreenEvent()
    object CheckSocketConnection: EnrollScreenEvent()
    object Verify: EnrollScreenEvent()
    object Completed: EnrollScreenEvent()
    object CANCEL: EnrollScreenEvent()
    object RESET: EnrollScreenEvent()

    data class TextFieldInput(val newEnrollUser: NewEnrollUser): EnrollScreenEvent()
    object ResetTextFieldInput: EnrollScreenEvent() // Do use me!
}