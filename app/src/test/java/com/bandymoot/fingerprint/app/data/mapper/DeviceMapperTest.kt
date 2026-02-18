package com.bandymoot.fingerprint.app.data.mapper

import com.bandymoot.fingerprint.app.data.dto.DeviceDto
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.time.Instant

class DeviceMapperTest {

    private val gson = Gson()
    private val fallbackDateString = "2026-02-16T05:28:40.667Z"
    private val expectedFallbackEpoch = Instant.parse(fallbackDateString).toEpochMilli()

    @Test
    fun `test mapping when backend returns null for device_last_seen`() {
        // 1. Arrange: 'device_last_seen' is explicitly null in the JSON
        val json = """{
            "device_name": "Scanner-01",
            "last_status": "active",
            "device_last_seen": null,
            "device_firmware_version": "1.0.0",
            "createdAt": "2026-01-01T10:00:00Z"
        }""".trimIndent()

        // 2. Act
        val dto = gson.fromJson(json, DeviceDto::class.java)
        val entity = dto.toEntity()

        // 3. Assert: Verify the fallback prevents a crash and satisfies the DB
        // In your current code, it uses a hardcoded fallback string
        assertEquals("null", entity?.name)
        assertEquals(expectedFallbackEpoch, entity?.lastSeenAt)
        println("Entity mapped successfully with fallback: ${entity?.lastSeenAt}")
    }

    @Test
    fun `test mapping when critical keys are missing entirely`() {
        // 1. Arrange: JSON is missing keys like 'device_name'
        val json = """{
            "last_status": "offline"
        }""".trimIndent()

        // 2. Act
        val dto = gson.fromJson(json, DeviceDto::class.java)
        val entity = dto.toEntity()

        // 3. Assert: Ensure defaults prevent NullPointerExceptions
        // Even if name is null in DTO, your Entity must handle it
        assertNotNull(entity)
        println("Mapped entity from empty JSON: $entity")
    }

    @Test
    fun `test mapping when firmware version is malformed`() {
        // 1. Arrange: Firmware is a weird string that isn't a standard version
        val json = """{
            "device_firmware_version": "INVALID_VERSION_STRING",
            "last_status": "unknown"
        }""".trimIndent()

        // 2. Act
        val dto = gson.fromJson(json, DeviceDto::class.java)
        // This will test if your parseVersionToLong helper is crash-proof
        val entity = dto.toEntity()

        // 3. Assert
        assertNotNull(entity)
        println("Mapped entity with invalid version string safely.")
    }
}