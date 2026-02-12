//package com.bandymoot.fingerprint.app.domain.model
//
//sealed class SocketEvent(val data: Any) {
//    class IDLE: SocketEvent(Unit)
//    class Connected : SocketEvent(Unit)
//    class Disconnected : SocketEvent(Unit)
//    data class DeviceStatus(val status: Any) : SocketEvent(status)
//    data class AttendanceStatus(val status: Any) : SocketEvent(status)
//    data class Message(val msg: Any) : SocketEvent(msg)
//}
