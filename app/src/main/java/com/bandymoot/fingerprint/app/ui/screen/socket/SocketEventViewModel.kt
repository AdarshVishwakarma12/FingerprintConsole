package com.bandymoot.fingerprint.app.ui.screen.socket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.data.socket.SocketManager
import com.bandymoot.fingerprint.app.domain.model.SocketEvent
import com.bandymoot.fingerprint.app.utils.AppConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocketViewModel @Inject constructor(
    private val tokenProvider: TokenProvider
): ViewModel() {

    private val _deviceStatus = MutableSharedFlow<Any>()
    val deviceStatus: SharedFlow<Any> = _deviceStatus

    private val _attendanceStatus = MutableSharedFlow<Any>()
    val attendanceStatus: SharedFlow<Any> = _attendanceStatus

    private val _message = MutableSharedFlow<Any>()
    val message: SharedFlow<Any> = _message

    private val _connectionState = MutableSharedFlow<Boolean>()
    val connectionState: SharedFlow<Boolean> = _connectionState

    fun startSocket(token: String = tokenProvider.getAccessToken() ?: "INVALID_TOKEN") {

        AppConstant.debugMessage("CONNECTING TO SOCKET!")

        SocketManager.setListener { event ->
            viewModelScope.launch {

                AppConstant.debugMessage("TheSocketEvent: ${event.data}")
                when (event) {
                    is SocketEvent.Connected -> {
                        _connectionState.emit(true)
                    }
                    is SocketEvent.Disconnected -> {
                        _connectionState.emit(false)
                    }
                    is SocketEvent.DeviceStatus -> {
                        _deviceStatus.emit(event.data)
                    }
                    is SocketEvent.AttendanceStatus -> {
                        _attendanceStatus.emit(event.data)
                    }
                    is SocketEvent.Message -> {
                        _message.emit(event.data)
                    }
                }
            }
        }

        SocketManager.init(token, AppConstant.SOCKET_URL)
        SocketManager.connect()
    }

    fun stopSocket() {
        SocketManager.disconnect()
    }

    fun sendMessage(event: String, data: Any) {
        SocketManager.emit(event, data)
    }
}
