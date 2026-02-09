package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.domain.model.AppError
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

fun Throwable.toAppError(): AppError {
    when (this) {
        is IOException -> {
            return AppError.Network()
        }

        is HttpException -> {
            return when (code()) {
                401 -> AppError.Unauthorized()
                404 -> AppError.NotFound()
                in 500..599 -> AppError.Server()
                else -> AppError.Unknown(message())
            }
        }

        is SocketTimeoutException -> {
            return AppError.Timeout()
        }

        else -> {
            AppError.Unknown(message)
        }
    }
    return AppError.Unknown(this.message ?: "Oops! Unexpected Error!")
}