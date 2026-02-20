package com.bandymoot.fingerprint.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.bandymoot.fingerprint.app.data.local.entity.DeviceEntity
import com.bandymoot.fingerprint.app.data.local.entity.DeviceStatusEntityEnum
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    @Query("SELECT * FROM devices")
    fun getAll(): Flow<List<DeviceEntity>>

    @Query("SELECT * FROM devices WHERE enrolled_by_server_manager_id=:managerId")
    fun getDevicesByManagerId(managerId: String): Flow<List<DeviceEntity>>

    @Query("SELECT * FROM devices WHERE server_device_id = :id LIMIT 1")
    suspend fun getById(id: String): DeviceEntity?

    @Query("SELECT * FROM devices WHERE organization_server_id = :orgId")
    suspend fun getByOrganization(orgId: String): List<DeviceEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: DeviceEntity)

    @Upsert
    suspend fun upsert(entity: DeviceEntity)

    @Query("UPDATE devices SET device_status = :newStatus WHERE device_code = :deviceCode")
    suspend fun updateDeviceStatusByCode(deviceCode: String, newStatus: DeviceStatusEntityEnum)

    @Delete
    suspend fun delete(entity: DeviceEntity)

    @Query("DELETE FROM devices")
    suspend fun deleteAll()
}
