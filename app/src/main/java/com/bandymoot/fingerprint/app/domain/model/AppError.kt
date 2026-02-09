package com.bandymoot.fingerprint.app.domain.model

sealed class AppError(
    override val message: String? = null
) : Throwable(message) {

    companion object

    class Network : AppError("Network error")
    class Unauthorized : AppError("Unauthorized")
    class NotFound : AppError("Not found")
    class Server : AppError("Server error")
    class Timeout : AppError("Timeout")

    class InValidDataEntered : AppError("Invalid Data")
    class Forbidden(message: String?) : AppError(message)
    class Conflict(message: String?) : AppError(message)
    class RateLimited(message: String?) : AppError(message)
    data class ServerCode(val code: Int, override val message: String?) : AppError()
    class Unknown(message: String?) : AppError(message)
}

fun AppError.Companion.fromHttpCode(
    code: Int,
    message: String? = null
): AppError {
    return when (code) {
        400 -> AppError.Unknown(message ?: "Bad request")
        401 -> AppError.Unauthorized()
        403 -> AppError.Forbidden(message)
        404 -> AppError.NotFound()
        409 -> AppError.Conflict(message)
        429 -> AppError.RateLimited(message)
        in 500..599 -> AppError.ServerCode(code, message)
        else -> AppError.Unknown(message ?: "HTTP $code")
    }
}
