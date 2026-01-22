package com.example.figerprintconsole.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "organizations",
    indices = [
        Index(value = ["server_organization_id"], unique = true)
    ]
)
data class OrganizationEntity(
    @PrimaryKey
    @ColumnInfo(name = "server_organization_id")
    val serverOrganizationId: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "contact_email")
    val contactEmail: String,

    @ColumnInfo(name = "license_key")
    val licenseKey: String,

    @ColumnInfo(name = "total_devices")
    val totalDevices: Int,

    @ColumnInfo(name = "total_users")
    val totalUsers: Int,

    @ColumnInfo(name = "is_active")
    val isActive: Boolean,

    @ColumnInfo(name = "created_at")
    val createdAt: Long
)
