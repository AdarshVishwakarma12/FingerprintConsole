package com.example.figerprintconsole.app.ui.screen.enroll.event

import com.example.figerprintconsole.app.domain.model.NewEnrollUser

sealed class EnrollScreenEvent {
    object LevelUp: EnrollScreenEvent()
    object IDLE: EnrollScreenEvent()
    object UserInput: EnrollScreenEvent()
    object EnrollingStepOne: EnrollScreenEvent()
    object EnrollingStepTwo: EnrollScreenEvent()
    object Verifying: EnrollScreenEvent()
    object Completed: EnrollScreenEvent()
    object CANCEL: EnrollScreenEvent()

    data class TextFieldInput(val newEnrollUser: NewEnrollUser): EnrollScreenEvent()
    object ResetTextFieldInput: EnrollScreenEvent() // Do use me!

    data class Error(val message: String?): EnrollScreenEvent()
}