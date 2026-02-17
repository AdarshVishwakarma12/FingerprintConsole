package com.bandymoot.fingerprint.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.bandymoot.fingerprint.app.data.local.entity.ManagerEntity

@Dao
interface ManagerDao {

    @Query("SELECT * FROM managers")
    suspend fun getAll(): List<ManagerEntity>

    @Query("SELECT * FROM managers WHERE server_manager_id = :id LIMIT 1")
    suspend fun getById(id: String): ManagerEntity?

    @Query("SELECT * FROM managers WHERE organization_server_id = :orgId")
    suspend fun getByOrganization(orgId: String): List<ManagerEntity>

    @Query("SELECT count(*) FROM managers")
    suspend fun getManagerCount(): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: ManagerEntity)

    @Upsert
    suspend fun upsert(entity: ManagerEntity)

    @Delete
    suspend fun delete(entity: ManagerEntity)

    @Query("DELETE FROM managers")
    suspend fun deleteAll()
}
