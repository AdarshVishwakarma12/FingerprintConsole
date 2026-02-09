package com.bandymoot.fingerprint.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["server_user_id"], unique = true),
        Index(value = ["organization_server_id"]),
        Index(value = ["enrolled_by_server_manager_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = OrganizationEntity::class,
            parentColumns = ["server_organization_id"],
            childColumns = ["organization_server_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ManagerEntity::class,
            parentColumns = ["server_manager_id"],
            childColumns = ["enrolled_by_server_manager_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "server_user_id")
    val serverUserId: String,

    @ColumnInfo(name = "user_code")
    val userCode: String,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "email")
    val email: String?,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "department")
    val department: String?,

    @ColumnInfo(name = "notes")
    val notes: String?,

    @ColumnInfo(name = "is_active")
    val isActive: Boolean,

    @ColumnInfo(name = "enrolled_at")
    val enrolledAt: Long,

    @ColumnInfo(name = "organization_server_id")
    val organizationServerId: String,

    @ColumnInfo(name = "enrolled_by_server_manager_id")
    val enrolledByServerManagerId: String?
)
