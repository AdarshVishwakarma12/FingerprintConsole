package com.example.figerprintconsole

import com.example.figerprintconsole.app.data.repository.EnrollmentRepositoryImpl
import com.example.figerprintconsole.app.data.websocket.TestWebSocket
import com.example.figerprintconsole.app.domain.model.EnrollmentSocketEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Test

class WebSocketLearningTest {

//    @Test
//    fun testWebsocket() {
//
//        // build client
//        val client = OkHttpClient()
//
//        // build request
//        val request = Request.Builder()
//            .url("ws://137.97.126.110:4005")
//            .build()
//
//        // build latch
//        val latch = CountDownLatch(1)
//
//        // Build the Listener
//        val listener = object : WebSocketListener() {
//            override fun onOpen(webSocket: WebSocket, response: Response) {
//                print("WS" + "Connected")
//                // webSocket.send("hello")
//            }
//
//            override fun onMessage(webSocket: WebSocket, text: String) {
//                println("WSMessage: $text")
//                latch.countDown()
//            }
//
//            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//                println("WSError: $t")
//                latch.countDown()
//            }
//
//            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
//                super.onClosed(webSocket, code, reason)
//            }
//        }
//
//        // new websocket
//        client.newWebSocket(request, listener)
//
//
//
//        val completed = latch.await(10, TimeUnit.SECONDS)
//        println("Test finished, latch released = $completed")
//    }
//
//    @Test
//    fun repository_connects_and_receives_events() = runBlocking {
//    val client = OkHttpClient()
//
//    val request = Request.Builder()
//        .url("ws://137.97.126.110:4005")
//        .build()
//
//    val testWebSocket = TestWebSocket(
//        client = client,
//        request = request
//    )
//
//    val repository = EnrollmentRepositoryImpl(
//        testWebSocket = testWebSocket
//    )
//
//    val events = mutableListOf<EnrollmentSocketEvent>()
//
//    val job = launch {
//        repository.observeEnrollment()
//            .collect { event ->
//                print("Event: $event")
//                events.add(event)
//            }
//    }
//
//    delay(500) // allow connection
//
//    repository.startEnrollment()
//
//    delay(2_000) // wait for echo response
//
//    repository.cancelEnrollment()
//
//    job.cancel()
//
//    // VERY light assertion (don’t overdo it yet)
//    // assert(events.isNotEmpty())
//}

}