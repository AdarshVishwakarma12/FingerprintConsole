package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.data.local.entity.UserEntity
import com.bandymoot.fingerprint.app.data.local.projection.UserEntityProjection
import com.bandymoot.fingerprint.app.domain.model.EnrollmentStatus
import com.bandymoot.fingerprint.app.domain.model.User
import com.bandymoot.fingerprint.app.domain.model.UserDetail
import com.bandymoot.fingerprint.app.utils.AppConstant
import java.time.Instant
import java.time.ZoneId

fun UserEntity.toDomain(): User {
    return User(
        uniqueServerId = serverUserId,
        employeeCode = userCode,
        fullName = fullName,
        email = email,
        phone = phone,
        department = department,
        notes = notes,
        isActive = isActive,
        enrollmentStatus = if(isActive) EnrollmentStatus.ENROLLED else EnrollmentStatus.NOT_ENROLLED,
        enrolledAt = enrolledAt.fromLongToDateString()
    )
}

fun UserEntityProjection.toDomain(): UserDetail {
    return UserDetail(
        userServerId = userEntity.serverUserId,
        userCode = userEntity.userCode,
        fullName = userEntity.fullName,
        email = userEntity.email,
        phone = userEntity.phone,
        department = userEntity.department,
        notes = userEntity.notes,
        isActive = userEntity.isActive,
        enrolledAt = userEntity.enrolledAt.fromLongToDateString(),
        organization = organizationEntity.toDomain(),
        enrolledBy = manager?.toDomain(),
        devices = fingerprintEntity.map { it.device.toDomain() },
        fingerprint = fingerprintEntity.map { it.fingerprintEntity.toDomain() },
        enrollmentStatus = null
    )
}

fun Long.fromLongToDateString(convertToDate: Boolean = true): String {
    return try {
        val instant =  Instant.ofEpochMilli(this)
            .atZone(ZoneId.of(AppConstant.ZONE_ID))

        if(convertToDate) {
            instant.toLocalDate().toString()
        } else {
            instant.toLocalTime().toString()
        }

    } catch (_ : Exception) {
        "ERR"
    }
}