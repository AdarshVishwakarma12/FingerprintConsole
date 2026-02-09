package com.bandymoot.fingerprint.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.bandymoot.fingerprint.app.data.local.entity.AuthenticationLogEntity

@Dao
interface AuthenticationLogDao {

    @Query("SELECT * FROM authenticate_logs")
    suspend fun getAll(): List<AuthenticationLogEntity>

    @Query("SELECT * FROM authenticate_logs WHERE server_authentication_log_id = :id LIMIT 1")
    suspend fun getById(id: String): AuthenticationLogEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: AuthenticationLogEntity)

    @Upsert
    suspend fun upsert(entity: AuthenticationLogEntity)

    @Delete
    suspend fun delete(entity: AuthenticationLogEntity)

    @Query("DELETE FROM authenticate_logs")
    suspend fun deleteAll()
}
