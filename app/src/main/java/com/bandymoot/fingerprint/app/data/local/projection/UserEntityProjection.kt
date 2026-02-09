package com.bandymoot.fingerprint.app.data.local.projection

import androidx.room.Embedded
import androidx.room.Relation
import com.bandymoot.fingerprint.app.data.local.entity.FingerprintEntity
import com.bandymoot.fingerprint.app.data.local.entity.ManagerEntity
import com.bandymoot.fingerprint.app.data.local.entity.OrganizationEntity
import com.bandymoot.fingerprint.app.data.local.entity.UserEntity

data class UserEntityProjection (

    @Embedded
    val userEntity: UserEntity,

    @Relation(
        entity = OrganizationEntity::class,
        parentColumn = "organization_server_id",
        entityColumn = "server_organization_id"
    )
    val organizationEntity: OrganizationEntity,

    @Relation(
        entity = ManagerEntity::class,
        parentColumn = "enrolled_by_server_manager_id",
        entityColumn = "server_manager_id"
    )
    val manager: ManagerEntity?,

    @Relation(
        entity = FingerprintEntity::class,
        parentColumn = "server_user_id",
        entityColumn = "user_server_id"
    )
    val fingerprintEntity: List<FingerprintEntityProjection>
)