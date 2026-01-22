package com.example.figerprintconsole.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "audit_logs",
    indices = [
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
data class AuditLogEntity(
    @PrimaryKey
    @ColumnInfo(name = "server_audit_log_id")
    val serverAuditLogId: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "action_type")
    val actionType: ActionType,

    @ColumnInfo(name = "entity_type")
    val entityType: FromEntity,

    @ColumnInfo(name = "entity_server_id")
    val entityServerId: String?,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "ip_address")
    val ipAddress: String?,

    @ColumnInfo(name = "organization_server_id")
    val organizationServerId: String
)

enum class ActionType {
    REPORT_GENERATED, SETTINGS_UPDATED, DELETE, ENABLE, DISABLE, USER_ENROLLED, DEVICE_ENROLLED, FINGERPRINT_ENROLLED, USER_STATUS_CHANGED, DEVICE_STATUS_CHANGED, LOGIN, REVOKE
}

enum class FromEntity {
    DEVICE, USER, MANAGER, FINGERPRINT
}
