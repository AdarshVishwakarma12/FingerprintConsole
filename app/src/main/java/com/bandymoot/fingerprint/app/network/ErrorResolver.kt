package com.bandymoot.fingerprint.app.network

import com.bandymoot.fingerprint.app.utils.AppConstant

object ErrorResolver {
    fun resolve(throwable: Throwable): String {
        // Log the full stack trace for the developer
        return when (throwable) {
            // 1. Connectivity Issues
            is java.net.UnknownHostException -> "No internet connection. Please check your WiFi."
            is java.net.SocketTimeoutException -> "The server is taking too long to respond."
            is java.net.ConnectException -> "Unable to reach the server. Please check your internet or try again later."
            is javax.net.ssl.SSLHandshakeException -> "Security certificate error. Connection not trusted."

            // 2. API / HTTP Issues
            is retrofit2.HttpException -> {
                when (throwable.code()) {
                    400 -> "Bad Request: The server didn't understand the data."
                    401 -> "Session expired. Please login again."
                    403 -> "Access denied. You don't have permission here."
                    404 -> "Resource not found on server."
                    429 -> "Too many requests. Please slow down."
                    in 500..599 -> "Server is having trouble. Try again later."
                    else -> "Network error: ${throwable.code()}"
                }
            }

            // 3. Data / Parsing Issues (Very common in Sockets!)
            is com.google.gson.JsonSyntaxException,
            is kotlinx.serialization.SerializationException -> {
                "Received invalid data format from the server."
            }

            // 4. Database Issues (Room)
            is android.database.sqlite.SQLiteFullException -> "Storage is full. Please clear some space."
            is android.database.sqlite.SQLiteDiskIOException -> "Disk error. Restart the app."
            is android.database.sqlite.SQLiteConstraintException -> "Database conflict. Data already exists."

            // 5. App State Issues
            is IllegalStateException -> throwable.message ?: "Application state error."
            is NullPointerException -> "Data error: Expected value was missing."

            // The catch-all for anything we missed
            else -> throwable.localizedMessage ?: "An unexpected error occurred!"
        }
    }

    fun resolveHttpError(code: Int): String {
        return when (code) {
            400 -> "Bad Request: The server didn't understand the request."
            401 -> "Session expired. Please login again."
            403 -> "Access denied. You don't have permission here."
            404 -> "Resource not found."
            408 -> "Request timeout. Try again."
            429 -> "Too many requests. Please slow down."
            in 500..599 -> "Server error. Our engineers are on it!"
            else -> "Something went wrong (Error $code)"
        }
    }
}