package com.example.figerprintconsole.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "devices",
    indices = [
        Index(value = ["server_device_id"], unique = true),
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
data class DeviceEntity(
    @PrimaryKey
    @ColumnInfo(name = "server_device_id")
    val serverDeviceId: String,

    @ColumnInfo(name = "device_code")
    val deviceCode: String,

    @ColumnInfo(name = "secret_key")
    val secretKey: String,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "location")
    val location: String?,

    @ColumnInfo(name = "device_status")
    val deviceStatus: DeviceStatusEntityEnum,

    @ColumnInfo(name = "last_seen_at")
    val lastSeenAt: Long,

    @ColumnInfo(name = "firmware_version")
    val firmwareVersion: Long,

    @ColumnInfo(name = "battery_level")
    val batteryLevel: Int?,

    @ColumnInfo(name = "enrolled_at")
    val enrolledAt: Long,

    @ColumnInfo(name = "organization_server_id")
    val organizationServerId: String,

    @ColumnInfo(name = "enrolled_by_server_manager_id")
    val enrolledByServerManagerId: String?,

    @ColumnInfo(name = "vendor")
    val vendor: String,

    @ColumnInfo(name = "supported_algorithms")
    val supportedAlgorithms: String,

    @ColumnInfo(name = "template_version")
    val templateVersion: String
)

enum class DeviceStatusEntityEnum {
    ACTIVE, INACTIVE, MAINTENANCE, OFFLINE
}
