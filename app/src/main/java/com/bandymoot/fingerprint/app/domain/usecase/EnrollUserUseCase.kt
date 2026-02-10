package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser
import com.bandymoot.fingerprint.app.domain.repository.EnrollmentRepository
import javax.inject.Inject

class EnrollUserUseCase @Inject constructor(
    private val enrollmentRepository: EnrollmentRepository
) {
    suspend operator fun invoke(newEnrollUser: NewEnrollUser): RepositoryResult<Boolean> {
        // I know it's too simple!
        return  enrollmentRepository.startEnrollment(newEnrollUser)
    }
}