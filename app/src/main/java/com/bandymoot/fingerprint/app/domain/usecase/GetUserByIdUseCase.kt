package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.domain.model.RepositoryResult
import com.bandymoot.fingerprint.app.data.repository.UserRepositoryImpl
import com.bandymoot.fingerprint.app.domain.model.User
import com.bandymoot.fingerprint.app.domain.model.UserDetail
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    val repository: UserRepositoryImpl
) {
    suspend operator fun invoke(user: User): RepositoryResult<UserDetail> {
        return repository.findDetailUserById(user.employeeCode)
    }
}