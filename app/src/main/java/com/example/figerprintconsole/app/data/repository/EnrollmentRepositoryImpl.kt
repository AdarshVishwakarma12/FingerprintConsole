package com.example.figerprintconsole.app.data.repository

import com.example.figerprintconsole.app.data.mapper.toJson
import com.example.figerprintconsole.app.data.websocket.EnrollmentSocketDataSourceImpl
import com.example.figerprintconsole.app.domain.model.EnrollmentSocketEvent
import com.example.figerprintconsole.app.domain.model.NewEnrollUser
import com.example.figerprintconsole.app.domain.repository.EnrollmentRepository
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import javax.inject.Inject

class EnrollmentRepositoryImpl @Inject constructor(
     val enrollmentSocketDataSourceImpl: EnrollmentSocketDataSourceImpl // We may have to define the di for this
): EnrollmentRepository {
    override fun observeEnrollment(): Flow<EnrollmentSocketEvent> {
        return enrollmentSocketDataSourceImpl.connect()
    }

    override suspend fun startEnrollment(newEnrollUser: NewEnrollUser) {
        enrollmentSocketDataSourceImpl.send(
            type = EnrollmentSocketEvent.StartEnrolling,
            message = buildEnrollNewUserJson(newEnrollUser)
        )
    }

    override suspend fun cancelEnrollment() {
        enrollmentSocketDataSourceImpl.send(
            message = cancelEnroll(),
            type = EnrollmentSocketEvent.CancelEnrolling
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