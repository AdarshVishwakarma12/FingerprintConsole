package com.bandymoot.fingerprint.app.data.websocket

import com.bandymoot.fingerprint.app.data.mapper.toWebsocketEvent
import com.bandymoot.fingerprint.app.domain.model.EnrollmentSocketEvent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class TestWebSocket @Inject constructor(
    val client: OkHttpClient,
    val request: Request
) {

    private var socket: WebSocket? = null

    fun connect(): Flow<EnrollmentSocketEvent> = callbackFlow {

        // Define Listener
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                socket = webSocket
                // AppConstant.debugMessage("WebSocket Connected", tag = "WS")
                trySend(EnrollmentSocketEvent.Connected)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                // AppConstant.debugMessage("WebSocket Received: $text", tag = "WS")
                trySend(text.toWebsocketEvent())
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                // AppConstant.debugMessage("WebSocket Failure", tag = "WS")
                trySend(
                    EnrollmentSocketEvent.VerificationFailed(t.message ?: "Unknown error")
                )
                close(t)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                // AppConstant.debugMessage("WebSocket Closed", tag = "WS")
            }
        }

        // build client
        client.newWebSocket(request, listener)

        awaitClose {
            socket?.close(1000, "Closed")
        }
    }

    fun send(message: String) {
        socket?.send(message)
    }

    fun disconnect() {
        socket?.close(1000, "bye")
    }
}
