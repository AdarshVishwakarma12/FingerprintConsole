package com.bandymoot.fingerprint.app.ui.screen.testScreen

sealed class TestScreenEvent {
    object LoadUsers: TestScreenEvent()
    object  InitialLoad: TestScreenEvent()
}