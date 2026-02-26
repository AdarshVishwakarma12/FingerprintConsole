package com.bandymoot.fingerprint.app.domain.repository

import com.bandymoot.fingerprint.app.domain.model.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.EnrollmentSocketEvent
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser
import kotlinx.coroutines.flow.Flow

interface EnrollmentRepository {
    fun observeEnrollment() : Flow<EnrollmentSocketEvent>

    suspend fun startEnrollment(newEnrollUser: NewEnrollUser): RepositoryResult<Boolean>

    suspend fun cancelEnrollment()
}