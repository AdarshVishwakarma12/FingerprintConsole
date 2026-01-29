package com.example.figerprintconsole.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "attendance_records",
    indices = [
        Index(value = ["user_server_id", "date"], unique = true),
        Index(value = ["organization_server_id"]),
        Index(value = ["month"])
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
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = OrganizationEntity::class,
            parentColumns = ["server_organization_id"],
            childColumns = ["organization_server_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AttendanceRecordEntity(
    @PrimaryKey
    val serverAttendanceId: String,

    @ColumnInfo(name = "user_server_id")
    val userServerId: String,

    @ColumnInfo(name = "date")
    val date: Long, // Start of day timestamp in milliseconds

    @ColumnInfo(name = "month")
    val month: Int, // zero-indexed

    @ColumnInfo(name = "check_in_time")
    val checkInTime: Long? = null,

    @ColumnInfo(name = "check_out_time")
    val checkOutTime: Long? = null,

    @ColumnInfo(name = "total_working_hours")
    val totalWorkingMinutes: Int = 0,

    @ColumnInfo(name = "break_minutes")
    val breakMinutes: Int = 0,

    @ColumnInfo(name = "overtime_minutes")
    val overtimeMinutes: Int = 0,

    @ColumnInfo(name = "status")
    val status: AttendanceStatus = AttendanceStatus.PRESENT,

    @ColumnInfo(name = "attendance_type")
    val attendanceType: AttendanceType = AttendanceType.REGULAR,

    @ColumnInfo(name = "remarks")
    val remarks: String? = null,

    @ColumnInfo(name = "approved_at")
    val approvedAt: Long? = null,

    @ColumnInfo(name = "is_modified")
    val isModified: Boolean = false,

    @ColumnInfo(name = "modified_reason")
    val modifiedReason: String? = null,

    @ColumnInfo(name = "modified_by_manager_id")
    val modifiedByManagerId: String? = null,

    @ColumnInfo(name = "device_server_id")
    val deviceServerId: String,

    @ColumnInfo(name = "organization_server_id")
    val organizationServerId: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
)

enum class AttendanceStatus {
    PRESENT, ABSENT, HALF_DAY, LATE, LEAVE, HOLIDAY, WEEKEND, ON_DUTY
}

enum class AttendanceType {
    REGULAR, OVERTIME, HOLIDAY_WORK, NIGHT_SHIFT, REMOTE
}