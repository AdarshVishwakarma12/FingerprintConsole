package com.example.figerprintconsole.app.ui.testScreen

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestViewModel: ViewModel() {

    private var isInitialLoaded: Boolean = false

    private var _isLoading = MutableStateFlow(false)
    var isLoading: StateFlow<Boolean> = _isLoading

    private  var _userList = MutableStateFlow<List<TestUserClass>>(emptyList<TestUserClass>())
    var userList: StateFlow<List<TestUserClass>> = _userList

    // Add multiple Event's click logic over here..
    fun onEvent(event: TestScreenEvent) {
        when(event) {
            is TestScreenEvent.InitialLoad -> {
                initialLoad()
            }
            is TestScreenEvent.LoadUsers -> {
                loadUsers()
            }
        }
    }

    fun initialLoad() {
        if(!isInitialLoaded) {
            onEvent(TestScreenEvent.LoadUsers)
        }
        isInitialLoaded = true
    }

    // Functions to handle each event..
    fun loadUsers() {
        viewModelScope.launch {
            _isLoading.value = true

            // Now Fetch the Value from repository...
            delay(3000)

            val successData = listOf(
                TestUserClass(
                    name = System.currentTimeMillis().toString()
                )
            )

            // Append new data
            _userList.value = _userList.value + successData

            _isLoading.value = false

        }
    }

}