package com.example.figerprintconsole.app.ui.screen.testScreen

sealed class TestScreenEvent {
    object LoadUsers: TestScreenEvent()
    object  InitialLoad: TestScreenEvent()
}