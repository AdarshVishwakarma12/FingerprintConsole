package com.bandymoot.fingerprint.app.data.remote

import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import retrofit2.Response

suspend fun <T: Any> safeApiCall(
    apiCall: suspend () -> Response<out T>
): RepositoryResult<T> {
    return try {

        val response = apiCall()

        if(!response.isSuccessful) {
            return RepositoryResult.Failed(
                Exception("HTTP ${response.code()}")
            )
        }

        val body = response.body()
            ?: return RepositoryResult.Failed(
                Exception("Response body is null")
            )

        RepositoryResult.Success(body)
    } catch (e: Exception) {
        RepositoryResult.Failed(e)
    }
}