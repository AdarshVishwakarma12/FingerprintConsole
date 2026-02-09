package com.bandymoot.fingerprint.app.ui.screen.month_picker.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthPickerBottomSheet(
    currentMonth: YearMonth,
    onMonthSelected: (YearMonth) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    disableFutureMonths: Boolean= true
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val today = YearMonth.now()

    var selectedYear by remember {
        mutableIntStateOf(currentMonth.year)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            YearSelector(
                year = selectedYear,
                onPreviousYear = { selectedYear-- },
                onNextYear = { selectedYear++ }
            )

            Spacer(Modifier.height(16.dp))

            MonthGrid(
                year = selectedYear,
                currentMonth = currentMonth,
                today = today,
                disableFutureMonths = disableFutureMonths,
                onMonthClick = { month ->
                    onMonthSelected(YearMonth.of(selectedYear, month))
                    onDismiss()
                }
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}