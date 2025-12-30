package com.example.figerprintconsole.app.data.remote

sealed class NetworkException(message: String) : Exception(message) {

    object NoInternet : NetworkException("No internet connection")

    data class ClientError(val code: Int, val error: String?) :
        NetworkException("Client error $code")

    data class ServerError(val code: Int) :
        NetworkException("Server error $code")

    object EmptyBody : NetworkException("Response body is empty")

    object NoUsers : NetworkException("No users enrolled")

    object NoUser: NetworkException("No user enrolled")

    data class Unknown(val throwable: Throwable) :
        NetworkException(throwable.message ?: "Unknown error")
}