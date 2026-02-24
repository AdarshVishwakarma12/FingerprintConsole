package com.bandymoot.fingerprint.app.data.socket

import com.bandymoot.fingerprint.app.data.mapper.toSocketTopicAttendance
import com.bandymoot.fingerprint.app.data.mapper.toSocketTopicDevice
import com.bandymoot.fingerprint.app.data.mapper.toSocketTopicEnrollProgress
import com.bandymoot.fingerprint.app.network.NetworkState
import com.bandymoot.fingerprint.app.utils.AppConstant
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.json.JSONObject

object SocketManager {

    private var socket: Socket? = null
    private var listener: ((event: SocketEvent) -> Unit)? = null // Limitation -> One listener at a time!

    // Time to move to Shared Flow (Use this inside Device Repo! Listen and update the DB) { TOPIC::DEVICE }
    private val _socketEvent: MutableSharedFlow<SocketEvent> = MutableSharedFlow()
    val socketEvent: SharedFlow<SocketEvent> get() = _socketEvent

    private var tokenJob: Job? = null
    private val managerScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val sharedFlowScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

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
            on(Socket.EVENT_CONNECT) { listener?.invoke(SocketEvent.Connected) }
            on(Socket.EVENT_DISCONNECT) { listener?.invoke(SocketEvent.Disconnected) }
            on(SocketTopic.TOPIC_DEVICE_STATUS) { args ->
                try {
                    val jsonObject = args[0] as JSONObject
                    val socketData = SocketEvent.Device(jsonObject.toSocketTopicDevice())
                    listener?.invoke(socketData)
                    sharedFlowScope.launch { _socketEvent.emit(socketData) }

                } catch (e: Exception) {
                    listener?.invoke(SocketEvent.Error(message = e.message?:e.localizedMessage))
                }
            }
            on(SocketTopic.TOPIC_ATTENDANCE_STATUS) { args ->
                try {
                    val jsonObject = args[0] as JSONObject
                    listener?.invoke(SocketEvent.Attendance(jsonObject.toSocketTopicAttendance()))
                } catch (e: Exception) {
                    listener?.invoke(SocketEvent.Error(message = e.message?:e.localizedMessage))
                }
            }
            on(SocketTopic.TOPIC_ENROLL_PROGRESS) { args ->
                try {
                    val jsonObject = args[0] as JSONObject
                    val event = SocketEvent.EnrollProgress(data = jsonObject.toSocketTopicEnrollProgress())
                    sharedFlowScope.launch { _socketEvent.emit(event) }
                } catch (e: Exception) {
                    val errorEvent = SocketEvent.Error(message = e.message ?: e.localizedMessage)
                    sharedFlowScope.launch { _socketEvent.emit(errorEvent) }
                }
            }
            on(SocketTopic.TOPIC_MESSAGE) { args ->  }
        }
    }

    // As soon as we have the token, we have a socket to listen to!
    // Add the NetworkManager Callback to retry to connect to Socket.IO
    // Just use the ViewModel, which explore the states! using SharedFlow
    fun observeToken(tokenFlow: StateFlow<String?>, networkState: StateFlow<NetworkState>) {
        // Cancel previous job if any
        // This runs in background which is Dispatchers.IO, and always collect the Latest data from TokenProvider
        // Combined with the NetworkManager

        tokenJob?.cancel()
        tokenJob = managerScope.launch {
            combine(tokenFlow, networkState) { token, network ->
               !token.isNullOrEmpty() && network is NetworkState.Available
            }
                .distinctUntilChanged()
                .collectLatest { shouldConnect ->
                    if(shouldConnect) {
                        init(tokenFlow.value ?: "INVALID-TOKEN")
                        connect()
                    } else {
                        disconnect()
                    }
                }
        }
    }

    fun connect() { socket?.connect() }

    fun disconnect() { socket?.disconnect() }

    fun hasActiveConnection(): Boolean = socket?.connected() == true

    fun emit(event: String, data: Any) { socket?.emit(event, data) }
}