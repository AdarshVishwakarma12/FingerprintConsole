package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.data.dto.UserServerResponseDto
import com.bandymoot.fingerprint.app.data.local.entity.UserEntity

fun UserServerResponseDto.toEntity(): UserEntity {
    return UserEntity(
        serverUserId = id,
        userCode = userRoll,
        fullName = userName,
        email = userEmail,
        phone = userPhone,
        department = userDepartment,
        notes = null, // Not provided by backend
        isActive = status.equals("ACTIVE", ignoreCase = true),
        enrolledAt = parseIsoToEpoch(createdAt),
        organizationServerId = organization.id,
        enrolledByServerManagerId = manager.id
    )
}


//fun String.isoStringToLocalDateTime(): LocalDateTime {
//    // Parse the ISO string as OffsetDateTime (Z = UTC)
//    val offsetDateTime = OffsetDateTime.parse(this)
//
//    // Convert to LocalDateTime in system default timezone
//    return offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
//}
