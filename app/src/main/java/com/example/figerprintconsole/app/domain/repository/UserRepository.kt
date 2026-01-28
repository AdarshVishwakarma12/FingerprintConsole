package com.example.figerprintconsole.app.domain.repository

import com.example.figerprintconsole.app.data.repository.RepositoryResult
import com.example.figerprintconsole.app.domain.model.User
import com.example.figerprintconsole.app.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface UserRepository {
    fun observeAll(): Flow<List<User>>
    suspend fun findDetailUserById(employeeCode: String): RepositoryResult<UserDetail>
    suspend fun delete(id: UUID): RepositoryResult<Unit>
    suspend fun sync(): RepositoryResult<Unit>
}