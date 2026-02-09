package com.bandymoot.fingerprint.app.ui.screen.month_picker.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.Month
import java.time.YearMonth

@Composable
fun MonthGrid(
    year: Int,
    currentMonth: YearMonth,
    today: YearMonth,
    disableFutureMonths: Boolean,
    onMonthClick: (Month) -> Unit
) {
    val months = Month.entries.toTypedArray()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(months) { month ->
            val yearMonth = YearMonth.of(year, month)

            val isSelected = yearMonth == currentMonth
            val isFuture =
                disableFutureMonths && yearMonth.isAfter(today)

            MonthItem(
                month = month,
                isSelected = isSelected,
                isEnabled = !isFuture,
                onClick = { onMonthClick(month) }
            )
        }
    }
}