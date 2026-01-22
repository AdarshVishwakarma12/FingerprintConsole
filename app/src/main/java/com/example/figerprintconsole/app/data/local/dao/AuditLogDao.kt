package com.example.figerprintconsole.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.figerprintconsole.app.data.local.entity.AuditLogEntity

@Dao
interface AuditLogDao {

    @Query("SELECT * FROM audit_logs")
    suspend fun getAll(): List<AuditLogEntity>

    @Query("SELECT * FROM audit_logs WHERE server_audit_log_id = :id LIMIT 1")
    suspend fun getById(id: String): AuditLogEntity?

    @Query("SELECT * FROM audit_logs WHERE organization_server_id = :orgId")
    suspend fun getByOrganization(orgId: String): List<AuditLogEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: AuditLogEntity)

    @Upsert
    suspend fun upsert(entity: AuditLogEntity)

    @Delete
    suspend fun delete(entity: AuditLogEntity)

    @Query("DELETE FROM audit_logs")
    suspend fun deleteAll()
}
