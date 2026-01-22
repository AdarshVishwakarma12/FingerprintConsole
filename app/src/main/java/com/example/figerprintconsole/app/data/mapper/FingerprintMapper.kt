package com.example.figerprintconsole.app.data.mapper

import com.example.figerprintconsole.app.data.local.entity.FingerprintEntity
import com.example.figerprintconsole.app.domain.model.Fingerprint

fun FingerprintEntity.toDomain(): Fingerprint {
    return Fingerprint(
        fingerIndex = fingerIndex,
        handType = handType.toString(),
        qualityScore = qualityScore,
        enrolledAt = enrolledAt.fromLongToDateString()
    )
}