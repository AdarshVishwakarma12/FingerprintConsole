package com.example.figerprintconsole.app.utils

import android.util.Log

object AppConstant {
    const val BASE_URL = "http://137.97.126.110:4005/"
    const val WEB_SOCKET_URL = "ws://137.97.126.110:4005"
    const val GET_ALL_USERS_API = "api/users"
    const val USER_GET_URL = "api/users/fp" // add the ID of user
    const val GET_USER_BY_ID_API = "api/users/fp"

    const val DEBUG = true

    const val ZONE_ID = "Asia/Kolkata"
    fun debugMessage(msg: String, tag: String = "UNKNOWN", debugType: DebugType = DebugType.INFO) {
        if(DEBUG) {
            if(debugType == DebugType.INFO) Log.i(tag, msg)
            if(debugType == DebugType.ERROR) Log.e(tag, msg)
            if(debugType == DebugType.DESCRIPTION) Log.d(tag, msg)
        }
    }
}

enum class DebugType {
    ERROR,
    INFO,
    DESCRIPTION
}