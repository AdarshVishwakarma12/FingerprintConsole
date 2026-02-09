package com.bandymoot.fingerprint.app.data.mapper

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId

//fun UserDto.toDomain(): User {
//    return User(
//        id = id,
//        fingerprintId = fingerprintId,
//        employeeId = employeeId,
//        name = name,
//        email = email ?: "Null",
//        profileImage = null,
//        enrollmentStatus = EnrollmentStatus.ENROLLED,
//        lastAccess = updatedAt.isoStringToLocalDateTime(),
//    )
//}

fun String.isoStringToLocalDateTime(): LocalDateTime {
    // Parse the ISO string as OffsetDateTime (Z = UTC)
    val offsetDateTime = OffsetDateTime.parse(this)

    // Convert to LocalDateTime in system default timezone
    return offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
}
