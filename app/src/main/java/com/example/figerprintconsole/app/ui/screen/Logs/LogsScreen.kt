package com.example.figerprintconsole.app.ui.screen.Logs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.figerprintconsole.app.ui.screen.Logs.components.SlideDateChange
import com.example.figerprintconsole.app.ui.screen.Logs.components.Tags
import com.example.figerprintconsole.app.ui.screen.Logs.components.UserLogCard
import com.example.figerprintconsole.app.ui.screen.Logs.event.LogsScreenUiEvent

@Preview
@Composable
fun LogsScreen(
    modifier: Modifier = Modifier,
    viewModel: LogsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = "Select Device",
            color = Color(0xFF6B6B6B), // soft neutral gray
            fontWeight = FontWeight.SemiBold
        )

        Tags(
            deviceList = uiState.devices,
            activeTag = uiState.currentDeviceSelected,
            onTagSelected = { selected -> viewModel.onEvent(LogsScreenUiEvent.SelectDevice(selected)) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = modifier.align(Alignment.CenterHorizontally)
        ) {
            SlideDateChange(
                currentDate = uiState.currentDate,
                formatter = uiState.formatter,
                onPrevious = { viewModel.onEvent(LogsScreenUiEvent.ChangeDateNegative) },
                onNext = { viewModel.onEvent(LogsScreenUiEvent.ChangeDatePositive) },
                slideDirection = uiState.slidingDirection,
                accentColor = uiState.currentDeviceSelected.color
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(6.dp)
        ) {
            items(10) { log ->
                UserLogCard()
            }
        }

    }
}
