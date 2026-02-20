package com.bandymoot.fingerprint.app.data.remote

import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.network.ErrorResolver
import retrofit2.Response

suspend fun <T: Any> safeApiCall(
    apiCall: suspend () -> Response<out T>
): RepositoryResult<T> {
    return try {

        val response = apiCall()

        if(!response.isSuccessful) {
            val errorMessage = ErrorResolver.resolveHttpError(response.code())
            return RepositoryResult.Failed(
                throwable = Exception("HTTP ${response.code()}"),
                descriptiveError = errorMessage
            )
        }

        val body = response.body()
            ?: return RepositoryResult.Failed(
                Exception("Response body is null")
            )

        RepositoryResult.Success(body)
    } catch (e: Exception) {
        RepositoryResult.Failed(throwable = e, descriptiveError = ErrorResolver.resolve(e))
    }
}