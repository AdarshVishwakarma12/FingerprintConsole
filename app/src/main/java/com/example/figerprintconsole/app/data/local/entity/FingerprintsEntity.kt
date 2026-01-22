package com.example.figerprintconsole.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "fingerprints",
    indices = [
        Index(value = ["user_server_id"]),
        Index(value = ["device_server_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["server_user_id"],
            childColumns = ["user_server_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = DeviceEntity::class,
            parentColumns = ["server_device_id"],
            childColumns = ["device_server_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FingerprintEntity(
    @PrimaryKey
    @ColumnInfo(name = "server_fingerprint_id")
    val serverFingerprintId: String,

    @ColumnInfo(name = "finger_index")
    val fingerIndex: Int,

    @ColumnInfo(name = "hand_type")
    val handType: HandType,

    @ColumnInfo(name = "quality_score")
    val qualityScore: Int?,

    @ColumnInfo(name = "enrolled_at")
    val enrolledAt: Long,

    @ColumnInfo(name = "enrolled_on_device_code")
    val enrolledOnDeviceCode: String,

    @ColumnInfo(name = "user_server_id")
    val userServerId: String,

    @ColumnInfo(name = "device_server_id")
    val deviceServerId: String
)

enum class HandType {
    LEFT, RIGHT
}
