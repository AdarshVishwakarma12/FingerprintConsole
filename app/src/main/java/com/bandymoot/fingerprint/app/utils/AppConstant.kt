package com.bandymoot.fingerprint.app.utils

import android.util.Log
import java.time.format.DateTimeFormatter

object AppConstant {
    const val SERVER_URL =  "192.168.0.139:4000" // "137.97.126.110:4003"
    const val BASE_URL = "http://$SERVER_URL/"
    const val WEB_SOCKET_URL = "ws://$SERVER_URL"
    const val SOCKET_URL = "http://$SERVER_URL"
    const val LOGIN_USER_API = "api/auth/login/manager"
    const val START_USER_ENROLLMENT = "/api/user/enrolled-user"
    const val GET_ALL_USERS_API = "api/user/get-users"
    const val USER_GET_URL = "api/users/fp" // add the ID of user
    const val GET_USER_BY_ID_API = "api/users/fp"
    const val GET_ALL_ORGANISATION_API = "/api/auth/get-organization"
    const val GET_ALL_MANAGER_API = "/api/auth/manager"
    const val GET_ALL_DEVICES_API = "/api/device/get-device"
    const val GET_ALL_ATTENDANCE_API = "/api/attendance/get-attendance/All/"
    const val ENROLL_NEW_DEVICE_API = "/api/device/add-device"

    const val DEBUG = true

    // Validation Constants
    const val MIN_PASSWORD_LENGTH = 4
    const val MAX_PASSWORD_LENGTH = 128

    // Enroll Timeout
    const val ENROLLMENT_TIMEOUT: Long = 35_000

    // SHARED PREFERENCE
    const val SHARED_PREF = "secure_prefs"
    const val SHARED_PREF_ACCESS_TOKEN = "access_token"
    const val SHARED_PREF_REFRESH_TOKEN = "refresh_token"
    const val SHARED_PREF_USER_EMAIL = "user_email"
    const val SHARED_PREF_LOGIN_TIMESTAMP = "login_timestamp"
    const val SHARED_PREF_USER_ID = "login_user_id"
    const val SHARED_PREF_CURRENT_USER = "current_user"

    // For LocalDate::
    // 6 => Saturday
    // 7 => Sunday
    val WEEKENDS_LIST = listOf(6, 7)

    const val ZONE_ID = "Asia/Kolkata"
    fun debugMessage(msg: String, tag: String = "UNKNOWN", debugType: DebugType = DebugType.INFO) {
        if(DEBUG) {
            if(debugType == DebugType.INFO) Log.i(tag, msg)
            if(debugType == DebugType.ERROR) Log.e(tag, msg)
            if(debugType == DebugType.DESCRIPTION) Log.d(tag, msg)
        }
    }

    // Api Attendnce Url pattern
    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

}

enum class DebugType {
    ERROR,
    INFO,
    DESCRIPTION
}