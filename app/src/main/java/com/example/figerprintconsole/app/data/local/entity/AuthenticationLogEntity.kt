package com.example.figerprintconsole.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "authenticate_logs",
    indices = [
        Index(value = ["device_server_id"]),
        Index(value = ["user_server_id"])
    ],
    foreignKeys = [

        // Additional
        // We can also connect the Organization!

        ForeignKey(
            entity = DeviceEntity::class,
            parentColumns = ["server_device_id"],
            childColumns = ["device_server_id"]
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["server_user_id"],
            childColumns = ["user_server_id"]
        )
    ]
)
data class AuthenticationLogEntity(
    @PrimaryKey
    @ColumnInfo(name = "server_authentication_log_id")
    val serverAuthenticationLogId: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

    @ColumnInfo(name = "result")
    val result: AuthResult,

    @ColumnInfo(name = "confidence_score")
    val confidenceScore: Int,

    @ColumnInfo(name = "attempt_duration_ms")
    val attemptDurationMs: Long?,

    @ColumnInfo(name = "device_server_id")
    val deviceServerId: String,

    @ColumnInfo(name = "user_server_id")
    val userServerId: String?,

    @ColumnInfo(name = "matched_fingerprint_server_id")
    val matchedFingerprintServerId: String?,

    @ColumnInfo(name = "purpose")
    val purpose: AuthenticationPurpose
)

enum class AuthResult {
    SUCCESS, FAILED, NO_MATCH, LOW_CONFIDENCE, ERROR
}

enum class AuthenticationPurpose {
    ATTENDANCE, ACCESS, VERIFICATION
}
