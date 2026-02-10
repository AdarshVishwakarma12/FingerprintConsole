package com.bandymoot.fingerprint.app.data.socket

import com.bandymoot.fingerprint.app.domain.model.SocketEvent
import com.bandymoot.fingerprint.app.utils.AppConstant
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

object SocketManager {

    private var socket: Socket? = null
    private var listener: ((event: SocketEvent) -> Unit)? = null
    private var tokenJob: Job? = null

    fun setListener(callback: (event: SocketEvent) -> Unit) {
        listener = callback
    }

    fun init(token: String, serverUrl: String = AppConstant.SOCKET_URL) {
        if(socket != null) return

        val options = IO.Options().apply {
            auth = mapOf("token" to token)
            transports = arrayOf("websocket")
            reconnection = true
            reconnectionAttempts = 10
            reconnectionDelay = 3000
        }

        socket = IO.socket(serverUrl, options)

        socket?.apply {
            on(Socket.EVENT_CONNECT) {
                AppConstant.debugMessage("CONNECTING SOCKET :: WORKED")
                listener?.invoke(SocketEvent.Connected())
            }
            on(Socket.EVENT_DISCONNECT) { listener?.invoke(SocketEvent.Disconnected()) }
            on("Device_Status") { args ->
                AppConstant.debugMessage("CONNECTING SOCKET :: WORKED :: DEVICE STATUS :: ${args[0]}")
                listener?.invoke(SocketEvent.DeviceStatus(args[0]))
            }
            on("Attendance_Status") { args ->
                listener?.invoke(SocketEvent.AttendanceStatus(args[0]))
                AppConstant.debugMessage("CONNECTING SOCKET :: WORKED :: ATTENDANCE STATUS :: ${args[0]}")
            }
            on("message") { args -> listener?.invoke(SocketEvent.Message(args[0])) }
        }
    }

    // As soon as we have the token, we have a socket to listen to!
    // Just use the ViewModel, which explore the states! using SharedFlow
    fun observeToken(tokenFlow: StateFlow<String?>) {
        // Cancel previous job if any
        tokenJob?.cancel()
        tokenJob = CoroutineScope(Dispatchers.IO).launch {
            tokenFlow.collectLatest { token ->
                if (!token.isNullOrEmpty()) {
                    init(token)
                    connect()
                } else {
                    disconnect()
                }
            }
        }
    }

    fun connect() {
        AppConstant.debugMessage("CONNECTING 2 Socket")
        socket?.connect()
    }

    fun disconnect() { socket?.disconnect() }

    fun hasActiveConnection(): Boolean = socket?.connected() == true

    fun emit(event: String, data: Any) { socket?.emit(event, data) }
}