package com.bandymoot.fingerprint.app.domain.model

import kotlinx.coroutines.flow.Flow

interface EnrollmentSocketDataSource {
    fun connect(): Flow<EnrollmentSocketEvent>
    fun disconnect()
    fun send(type: EnrollmentSocketCommand, message: String)
}