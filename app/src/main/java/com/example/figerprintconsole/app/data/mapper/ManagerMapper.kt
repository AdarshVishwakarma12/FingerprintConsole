package com.example.figerprintconsole.app.data.mapper

import com.example.figerprintconsole.app.data.local.entity.ManagerEntity
import com.example.figerprintconsole.app.domain.model.Manager

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