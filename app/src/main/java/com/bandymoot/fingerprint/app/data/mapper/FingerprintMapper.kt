package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.data.local.entity.FingerprintEntity
import com.bandymoot.fingerprint.app.domain.model.Fingerprint

fun FingerprintEntity.toDomain(): Fingerprint {
    return Fingerprint(
        fingerIndex = fingerIndex,
        handType = handType.toString(),
        qualityScore = qualityScore,
        enrolledAt = enrolledAt.fromLongToDateString()
    )
}