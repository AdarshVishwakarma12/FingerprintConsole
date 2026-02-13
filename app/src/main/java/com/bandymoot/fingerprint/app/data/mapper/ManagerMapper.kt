package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.data.dto.ManagerDto
import com.bandymoot.fingerprint.app.data.local.entity.ManagerEntity
import com.bandymoot.fingerprint.app.domain.model.Manager

fun ManagerDto.toEntity(): ManagerEntity {
    return ManagerEntity(
        serverManagerId = id,
        username = managerEmail.substringBefore("@"),
        fullName = managerName,
        email = managerEmail,
        isSuperior = role.equals("Superior", ignoreCase = true),
        isActive = status,
        lastLoginAt = 0L, // API does not provide this
        createdAt = parseIsoToEpoch(createdAt),
        organizationServerId = organization.id
    )
}


fun ManagerEntity.toDomain(): Manager {
    return Manager(
        userName = username,
        fullName = fullName,
        email = email,
        isSuperior = isSuperior,
        isActive = isActive,
        lastLoginAt = lastLoginAt.fromLongToDateString(),
        createdAt = createdAt.fromLongToDateString(),

    )
}