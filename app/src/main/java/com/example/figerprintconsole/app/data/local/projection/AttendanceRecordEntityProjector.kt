package com.example.figerprintconsole.app.data.local.projection

import androidx.room.Embedded
import androidx.room.Relation
import com.example.figerprintconsole.app.data.local.entity.AttendanceRecordEntity
import com.example.figerprintconsole.app.data.local.entity.UserEntity

data class AttendanceRecordEntityProjector (
    @Embedded
    val attendanceRecordEntity: AttendanceRecordEntity,

    @Relation(
        entity = UserEntity::class,
        parentColumn = "user_server_id",
        entityColumn = "server_user_id"
    )
    val user: UserEntity
)