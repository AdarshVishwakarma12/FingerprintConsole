package com.example.figerprintconsole.app.data.mapper

import com.example.figerprintconsole.app.data.local.entity.OrganizationEntity
import com.example.figerprintconsole.app.domain.model.Organization

fun OrganizationEntity.toDomain(): Organization {
    return Organization(
        name = name,
        contactEmail = contactEmail,
        licenseKey = licenseKey,
        totalDevices = totalDevices,
        totalUsers = totalUsers,
        isActive = isActive,
        createdAt = createdAt.fromLongToDateString()
    )
}