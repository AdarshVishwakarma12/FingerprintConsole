package com.bandymoot.fingerprint.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.bandymoot.fingerprint.app.data.local.entity.UserEntity
import com.bandymoot.fingerprint.app.data.local.projection.UserEntityProjection
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Transaction
    @Query("SELECT * FROM users WHERE user_code=:employeeCode")
    suspend fun getUserWithDetailById(employeeCode: String): UserEntityProjection

    @Transaction
    @Query("SELECT * FROM users WHERE server_user_id=:serverId")
    suspend fun getUserWithDetailByServerId(serverId: String): UserEntityProjection

    @Query("SELECT * FROM users ORDER BY full_name")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE server_user_id = :id LIMIT 1")
    suspend fun getById(id: String): UserEntity?

    @Query("SELECT * FROM users WHERE organization_server_id = :orgId")
    suspend fun getByOrganization(orgId: String): List<UserEntity>

    @Query("SELECT server_user_id FROM users")
    suspend fun getAllUserIds(): List<String>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: UserEntity)

    @Upsert
    suspend fun upsert(entity: UserEntity)

    @Delete
    suspend fun delete(entity: UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}
