package com.example.figerprintconsole.app.data.mapper

import com.example.figerprintconsole.app.data.local.entity.UserEntity
import com.example.figerprintconsole.app.data.local.projection.UserEntityProjection
import com.example.figerprintconsole.app.domain.model.User
import com.example.figerprintconsole.app.domain.model.UserDetail
import com.example.figerprintconsole.app.utils.AppConstant
import java.time.Instant
import java.time.ZoneId

fun UserEntity.toDomain(): User {
    return User(
        employeeCode = userCode,
        fullName = fullName,
        email = email,
        phone = phone,
        department = department,
        notes = notes,
        isActive = isActive,
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

    } catch (e : Exception) {
        "ERR"
    }
}