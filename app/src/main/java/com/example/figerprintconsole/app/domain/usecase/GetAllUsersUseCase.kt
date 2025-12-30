package com.example.figerprintconsole.app.domain.usecase

import com.example.figerprintconsole.app.data.repository.UserRepositoryImpl
import com.example.figerprintconsole.app.domain.model.User
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    val repositoryImpl: UserRepositoryImpl
) {

    suspend operator fun invoke(): Result<List<User>> {
        // Validate all the inputs
        return repositoryImpl.getAllUsers()
    }

}