package com.example.figerprintconsole.app.domain.usecase

import com.example.figerprintconsole.app.data.repository.UserRepositoryImpl
import com.example.figerprintconsole.app.domain.model.User
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    val repository: UserRepositoryImpl
) {
//    suspend operator fun invoke(user: User): Result<User> {
//        return repository.observeAll()
//    }
}