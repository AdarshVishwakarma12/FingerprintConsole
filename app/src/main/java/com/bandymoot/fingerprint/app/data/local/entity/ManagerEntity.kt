package com.bandymoot.fingerprint.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "managers",
    indices = [
        Index(value = ["server_manager_id"], unique = true),
        Index(value = ["organization_server_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = OrganizationEntity::class,
            parentColumns = ["server_organization_id"],
            childColumns = ["organization_server_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ManagerEntity(
    @PrimaryKey
    @ColumnInfo(name = "server_manager_id")
    val serverManagerId: String,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "is_superior")
    val isSuperior: Boolean,

    @ColumnInfo(name = "is_active")
    val isActive: Boolean,

    @ColumnInfo(name = "last_login_at")
    val lastLoginAt: Long,

    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    @ColumnInfo(name = "organization_server_id")
    val organizationServerId: String
)
