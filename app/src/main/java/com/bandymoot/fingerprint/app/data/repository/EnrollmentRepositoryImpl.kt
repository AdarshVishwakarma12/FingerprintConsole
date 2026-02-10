package com.bandymoot.fingerprint.app.data.repository

import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.data.mapper.toJson
import com.bandymoot.fingerprint.app.data.mapper.toRequestDto
import com.bandymoot.fingerprint.app.data.remote.api.ApiServices
import com.bandymoot.fingerprint.app.data.websocket.EnrollmentSocketDataSourceImpl
import com.bandymoot.fingerprint.app.domain.model.EnrollmentSocketCommand
import com.bandymoot.fingerprint.app.domain.model.EnrollmentSocketEvent
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser
import com.bandymoot.fingerprint.app.domain.repository.EnrollmentRepository
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import java.lang.Exception
import javax.inject.Inject

class EnrollmentRepositoryImpl @Inject constructor(
    private val enrollmentSocketDataSourceImpl: EnrollmentSocketDataSourceImpl,
    private val apiServices: ApiServices,
    private val tokenProvider: TokenProvider
): EnrollmentRepository {
    override fun observeEnrollment(): Flow<EnrollmentSocketEvent> {
        return enrollmentSocketDataSourceImpl.connect()
    }

    override suspend fun startEnrollment(newEnrollUser: NewEnrollUser): RepositoryResult<Boolean> {
        try {
            val response = apiServices.startEnrollment(token = tokenProvider.getAccessToken() ?: "Invalid token", requestBody = newEnrollUser.toRequestDto())
            if(!response.isSuccessful) return RepositoryResult.Failed(Exception("Unsuccess Response"))
            return RepositoryResult.Success(true)
        } catch (e: Exception) {
            return RepositoryResult.Failed(e)
        }
    }

    // Cancel enroll is not working! from backend..!
    override suspend fun cancelEnrollment() {
        enrollmentSocketDataSourceImpl.send(
            message = cancelEnroll(),
            type = EnrollmentSocketCommand.CancelEnrolling
        )
        enrollmentSocketDataSourceImpl.disconnect()
    }
}

fun buildEnrollNewUserJson(newEnrollUser: NewEnrollUser): String {
    val payload = newEnrollUser.toJson()

    val root = JSONObject().apply {
        put("type", "ENROLL_NEW_USER")
        put("payload", payload)
    }

    return root.toString()
}

fun cancelEnroll(): String {
    val root = JSONObject().apply {
        put("type", "CANCEL_ENROLL")
    }

    return root.toString()
}