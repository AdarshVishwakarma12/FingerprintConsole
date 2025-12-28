package com.example.figerprintconsole.app.ui.testScreen

sealed class TestScreenEvent {
    object LoadUsers: TestScreenEvent()
    object  InitialLoad: TestScreenEvent()
}