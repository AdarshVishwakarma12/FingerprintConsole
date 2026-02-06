package com.example.figerprintconsole.app.domain.usecase

import com.example.figerprintconsole.app.data.repository.RepositoryResult
import com.example.figerprintconsole.app.domain.model.AttendanceRecord
import com.example.figerprintconsole.app.domain.repository.AttendanceRepository
import com.example.figerprintconsole.app.utils.AppConstant
import java.time.LocalDate
import javax.inject.Inject

// No Current use / as LazyColumn won't allow Maps DS!
// This class is the conversation of list of data into map!
// It's easier to deal with in ui!
class GetAttendanceGroupedByDate @Inject constructor(
    private val attendanceRepositoryImpl: AttendanceRepository
) {
    suspend operator fun invoke(userServerId: String, date: LocalDate): RepositoryResult<Map<String, List<AttendanceRecord>>> {

        val daysInMonth = date.lengthOfMonth()
        val dates = (1..daysInMonth).map { day ->
            LocalDate.of(date.year, date.month, day)
        }

        val result = attendanceRepositoryImpl.getAttendanceByMonthAndUser(month = date.month.value, userId = userServerId)

        return when(result) {
            is RepositoryResult.Success -> {
                AppConstant.debugMessage("Show Option:::: ${result.data}")
                val map = dates.associate { day ->
                    val key = day.toString()
                    key to result.data.filter { it.dateString == key }
                }
                RepositoryResult.Success(map) // Must take multiple values!
            }
            is RepositoryResult.Failed -> {
                result
            }
        }
    }
}