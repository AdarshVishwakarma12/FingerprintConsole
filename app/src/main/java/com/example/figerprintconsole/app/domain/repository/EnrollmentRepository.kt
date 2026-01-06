package com.example.figerprintconsole.app.domain.repository

import com.example.figerprintconsole.app.domain.model.EnrollmentSocketEvent
import com.example.figerprintconsole.app.domain.model.NewEnrollUser
import kotlinx.coroutines.flow.Flow

interface EnrollmentRepository {
    fun observeEnrollment() : Flow<EnrollmentSocketEvent>

    suspend fun startEnrollment(newEnrollUser: NewEnrollUser)

    suspend fun cancelEnrollment()
}