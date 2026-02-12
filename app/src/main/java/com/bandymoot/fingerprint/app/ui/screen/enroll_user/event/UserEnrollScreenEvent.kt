package com.bandymoot.fingerprint.app.ui.screen.enroll_user.event

import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser

sealed class UserEnrollScreenEvent {
    object StartEnrollment: UserEnrollScreenEvent()
    object ValidateUserInfoAndStartBiometric: UserEnrollScreenEvent()
    object CheckSocketConnection: UserEnrollScreenEvent()
    object Verify: UserEnrollScreenEvent()
    object Completed: UserEnrollScreenEvent()
    object CANCEL: UserEnrollScreenEvent()
    object RESET: UserEnrollScreenEvent()

    data class TextFieldInput(val newEnrollUser: NewEnrollUser): UserEnrollScreenEvent()
    object ResetTextFieldInput: UserEnrollScreenEvent() // Do use me!
}