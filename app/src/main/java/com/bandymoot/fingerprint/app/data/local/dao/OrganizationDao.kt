package com.bandymoot.fingerprint.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.bandymoot.fingerprint.app.data.local.entity.OrganizationEntity

@Dao
interface OrganizationDao {

    @Query("SELECT * FROM organizations")
    suspend fun getAll(): List<OrganizationEntity>

    @Query("SELECT * FROM organizations WHERE server_organization_id = :id LIMIT 1")
    suspend fun getById(id: String): OrganizationEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: OrganizationEntity)

    @Upsert
    suspend fun upsert(entity: OrganizationEntity)

    @Delete
    suspend fun delete(entity: OrganizationEntity)

    @Query("DELETE FROM organizations")
    suspend fun deleteAll()
}
