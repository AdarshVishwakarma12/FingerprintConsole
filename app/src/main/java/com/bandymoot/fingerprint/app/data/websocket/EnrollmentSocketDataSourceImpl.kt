package com.bandymoot.fingerprint.app.data.websocket

import com.bandymoot.fingerprint.app.data.mapper.toWebsocketEvent
import com.bandymoot.fingerprint.app.domain.model.EnrollmentSocketCommand
import com.bandymoot.fingerprint.app.domain.model.EnrollmentSocketDataSource
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

class EnrollmentSocketDataSourceImpl @Inject constructor(
    private val client: OkHttpClient,
    private val request: Request
): EnrollmentSocketDataSource {

    private var socket: WebSocket? = null

    override fun connect(): Flow<EnrollmentSocketEvent> = callbackFlow {
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) { trySend(EnrollmentSocketEvent.Connected) }
            override fun onMessage(webSocket: WebSocket, text: String) { trySend(text.toWebsocketEvent()) }
            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                trySend(EnrollmentSocketEvent.VerificationFailed(t.message ?: "Unknown error"))
                close(t)
            }
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) { trySend(EnrollmentSocketEvent.Disconnected) }
        }

        try {
            val socket = client.newWebSocket(request, listener)
            awaitClose { socket.close(1000, "Closed") }
        } catch (e: Exception) {
            trySend(
                EnrollmentSocketEvent.VerificationFailed(
                    e.message ?: "Connection failed"
                )
            )
            close(e)
        }
    }

    override fun disconnect() { client.dispatcher.executorService.shutdown() } // Shut Down at Client Level!

    override fun send(type: EnrollmentSocketCommand, message: String) {
        val ws = socket
        if (ws == null) {
            // socket not connected yet
            return
        }

        ws.send(message)
    }
}