package com.example.figerprintconsole.app.ui.screen.enroll.event

sealed class EnrollScreenEvent {
    object LevelUp: EnrollScreenEvent()
    object IDLE: EnrollScreenEvent()
    object UserInput: EnrollScreenEvent()
    object EnrollingStepOne: EnrollScreenEvent()
    object EnrollingStepTwo: EnrollScreenEvent()
    object Verifying: EnrollScreenEvent()
    object Completed: EnrollScreenEvent()
    object CANCEL: EnrollScreenEvent()
    data class Error(val message: String?): EnrollScreenEvent()
}