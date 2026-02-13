package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.data.dto.OrganizationDto
import com.bandymoot.fingerprint.app.data.local.entity.OrganizationEntity
import com.bandymoot.fingerprint.app.domain.model.Organization

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

fun OrganizationDto.toEntity(): OrganizationEntity {
    return OrganizationEntity(
        serverOrganizationId = id,
        name = orgName,
        contactEmail = orgEmail,
        licenseKey = licenseKey,
        totalDevices = totalDevices,
        totalUsers = totalUsers,
        isActive = status,
        createdAt = parseIsoToEpoch(createdAt)
    )
}