package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.data.repository.UserRepositoryImpl
import com.bandymoot.fingerprint.app.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    val repositoryImpl: UserRepositoryImpl
) {
    operator fun invoke(): Flow<List<User>> {
        // Validate all the inputs
        return repositoryImpl.observeAll()
    }
}