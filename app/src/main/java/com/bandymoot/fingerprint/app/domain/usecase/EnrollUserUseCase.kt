package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser
import com.bandymoot.fingerprint.app.domain.repository.EnrollmentRepository
import javax.inject.Inject

class EnrollUserUseCase @Inject constructor(
    private val enrollmentRepository: EnrollmentRepository
) {
    suspend operator fun invoke(newEnrollUser: NewEnrollUser): RepositoryResult<Boolean> {

        if(
            newEnrollUser.name.isEmpty() ||
            newEnrollUser.email.isEmpty() ||
            newEnrollUser.employeeId.isEmpty() ||
            newEnrollUser.phone.isEmpty() ||
            newEnrollUser.department.isEmpty() ||
            newEnrollUser.deviceId.isEmpty()
        ) {
            RepositoryResult.Failed(Exception("All Fields are required!"))
        }


        // Extensively check for Device ID:
        // if it fails the validation! Must return the Failed Status Before Hand

        return  enrollmentRepository.startEnrollment(newEnrollUser) // I think it can return
    }
}