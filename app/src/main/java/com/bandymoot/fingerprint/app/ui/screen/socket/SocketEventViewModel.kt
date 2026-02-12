package com.bandymoot.fingerprint.app.ui.screen.socket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.data.socket.SocketEvent
import com.bandymoot.fingerprint.app.data.socket.SocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocketEventViewModel @Inject constructor(

): ViewModel() {

    private val _events = MutableStateFlow<SocketEvent>(SocketEvent.IDLE)
    val events: StateFlow<SocketEvent> = _events

    init {
        SocketManager.setListener { event ->
            viewModelScope.launch { _events.emit(event) }
        }
    }
}