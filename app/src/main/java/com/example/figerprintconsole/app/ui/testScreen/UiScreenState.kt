package com.example.figerprintconsole.app.ui.testScreen

sealed class UiScreenState {
    class Loading(): UiScreenState()
    class Success(val data: List<TestUserClass>?): UiScreenState()
    class Error(val error: String?): UiScreenState()
}

data class TestUserClass(
    val name: String? = null
)