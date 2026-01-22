package com.example.figerprintconsole.app.domain.usecase

import com.example.figerprintconsole.app.data.repository.RepositoryResult
import com.example.figerprintconsole.app.data.repository.UserRepositoryImpl
import com.example.figerprintconsole.app.domain.model.User
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