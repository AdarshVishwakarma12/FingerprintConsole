package com.example.figerprintconsole.app.data.local.projection

import androidx.room.Embedded
import androidx.room.Relation
import com.example.figerprintconsole.app.data.local.entity.DeviceEntity
import com.example.figerprintconsole.app.data.local.entity.FingerprintEntity

data class FingerprintEntityProjection (
    @Embedded
    val fingerprintEntity: FingerprintEntity,

    @Relation(
        entity = DeviceEntity::class,
        parentColumn = "device_server_id",
        entityColumn = "server_device_id"
    )
    val device: DeviceEntity
)