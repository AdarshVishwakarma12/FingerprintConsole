package com.bandymoot.fingerprint.app.domain.repository

import com.bandymoot.fingerprint.app.domain.model.RepositoryResult
import com.bandymoot.fingerprint.app.domain.model.User
import com.bandymoot.fingerprint.app.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface UserRepository {
    fun observeAll(): Flow<List<User>>
    suspend fun findDetailUserById(employeeCode: String): RepositoryResult<UserDetail>
    suspend fun findDetailUserByServerId(userServerId: String): RepositoryResult<UserDetail>
    suspend fun getAllUserIds(): RepositoryResult<List<String>>
    suspend fun delete(id: UUID): RepositoryResult<Unit>
    suspend fun sync(): RepositoryResult<Unit>
}