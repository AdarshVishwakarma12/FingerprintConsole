package com.example.figerprintconsole.app.data.mapper

import com.example.figerprintconsole.app.data.local.entity.UserEntity
import com.example.figerprintconsole.app.data.local.projection.UserEntityProjection
import com.example.figerprintconsole.app.domain.model.User
import com.example.figerprintconsole.app.domain.model.UserDetail

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

fun Long.fromLongToDateString(): String {
    return this.toString()
}