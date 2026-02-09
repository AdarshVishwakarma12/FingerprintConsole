package com.bandymoot.fingerprint.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.bandymoot.fingerprint.app.data.local.entity.FingerprintEntity

@Dao
interface FingerprintDao {

    @Query("SELECT * FROM fingerprints")
    suspend fun getAll(): List<FingerprintEntity>

    @Query("SELECT * FROM fingerprints WHERE server_fingerprint_id = :id LIMIT 1")
    suspend fun getById(id: String): FingerprintEntity?

    @Query("SELECT * FROM fingerprints WHERE user_server_id = :userId")
    suspend fun getByUser(userId: String): List<FingerprintEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: FingerprintEntity)

    @Upsert
    suspend fun upsert(entity: FingerprintEntity)

    @Delete
    suspend fun delete(entity: FingerprintEntity)

    @Query("DELETE FROM fingerprints")
    suspend fun deleteAll()
}
