package com.bandymoot.fingerprint.app.domain.usecase

import com.bandymoot.fingerprint.app.data.repository.RepositoryResult
import com.bandymoot.fingerprint.app.domain.repository.AttendanceRepository
import com.bandymoot.fingerprint.app.utils.AppConstant
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetAttendanceByUserAndMonthUseCaseTest {

    // Setup Repository and UseCase
    private val repository: AttendanceRepository = mockk()
    private val useCase = GetAttendanceByUserAndMonthUseCase(repository)

    @Test
    fun `test the startOfMonth and endOfMonth values`() = runTest {

        // Global
        val zoneId = ZoneId.of(AppConstant.ZONE_ID)
        val userId = "EMP-001"

        // Given Argument -> February 2026 { Not a Leap Year - 28 Days }
        clearMocks(repository)
        callMe(
            yearMonth = YearMonth.of(2026, 2),
            userId = userId,
            expectedStart = LocalDate.of(2026, 2, 1).atStartOfDay(zoneId).toInstant().toEpochMilli(),
            expectedEnd = LocalDate.of(2026, 2, 28).atStartOfDay(zoneId).toInstant().toEpochMilli()
        )

        // Given Argument -> February 2024 { Leap Year - 29 Days }
        clearMocks(repository)
        callMe(
            yearMonth = YearMonth.of(2024, 2),
            userId = userId,
            expectedStart = LocalDate.of(2024, 2, 1).atStartOfDay(zoneId).toInstant().toEpochMilli(),
            expectedEnd = LocalDate.of(2024, 2, 29).atStartOfDay(zoneId).toInstant().toEpochMilli()
        )

        // Given Argument -> April 2026 { 30 Days }
        clearMocks(repository)
        callMe(
            yearMonth = YearMonth.of(2026, 4),
            userId = userId,
            expectedStart = LocalDate.of(2026, 4, 1).atStartOfDay(zoneId).toInstant().toEpochMilli(),
            expectedEnd = LocalDate.of(2026, 4, 30).atStartOfDay(zoneId).toInstant().toEpochMilli()
        )

        // Given Argument -> January 2026 { 31 Days }
        clearMocks(repository)
        callMe(
            yearMonth = YearMonth.of(2026, 1),
            userId = userId,
            expectedStart = LocalDate.of(2026, 1, 1).atStartOfDay(zoneId).toInstant().toEpochMilli(),
            expectedEnd = LocalDate.of(2026, 1, 31).atStartOfDay(zoneId).toInstant().toEpochMilli()
        )

        // Given Argument -> December 2025 { Check Edges }
        clearMocks(repository)
        callMe(
            yearMonth = YearMonth.of(2025, 1),
            userId = userId,
            expectedStart = LocalDate.of(2025, 1, 1).atStartOfDay(zoneId).toInstant().toEpochMilli(),
            expectedEnd = LocalDate.of(2025, 1, 31).atStartOfDay(zoneId).toInstant().toEpochMilli()
        )
    }

    fun callMe(yearMonth: YearMonth, userId: String, expectedStart: Long, expectedEnd: Long) = runTest {
        // Result
        coEvery { repository.getAttendanceByMonthAndUser(any(), any(), userId) } returns RepositoryResult.Success(data = emptyList())

        // Capture values changes
        val startSlot = slot<Long>()
        val endSlot = slot<Long>()

        useCase(yearMonth, userId)

        coVerify {
            repository.getAttendanceByMonthAndUser(
                capture(startSlot),
                capture(endSlot),
                userId
            )
        }

        // Test with LocalDate (startDate, endDate, difference)
        println()
        println("START VALUE: ${startSlot.captured} && EXPECTED VALUE: $expectedStart")
        println("END VALUE: ${endSlot.captured} && EXPECTED VALUE: $expectedEnd")
        println("DIFF VALUE: ${endSlot.captured - startSlot.captured} && EXPECTED VALUE: ${expectedEnd - expectedStart}")

        assertEquals(expectedStart, startSlot.captured)
        assertEquals(expectedEnd, endSlot.captured)
        assertTrue { (endSlot.captured - startSlot.captured) == expectedEnd - expectedStart }

        startSlot.clear()
        endSlot.clear()
    }
}