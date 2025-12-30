package com.example.figerprintconsole.app.domain.repository

import com.example.figerprintconsole.app.domain.model.User

interface UserRepository {

    suspend fun getAllUsers(): Result<List<User>>
    suspend fun getUserById(userId: Int): Result<User>
    suspend fun deleteUserById(userId: Int): Result<Boolean>
}