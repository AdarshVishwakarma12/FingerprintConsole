package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.data.local.entity.ManagerEntity
import com.bandymoot.fingerprint.app.domain.model.Manager

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