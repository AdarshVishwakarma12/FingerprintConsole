package com.example.figerprintconsole.app.ui.screen.logs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.figerprintconsole.app.domain.model.AttendanceRecord
import com.example.figerprintconsole.app.ui.screen.logs.components.SlideDateChange
import com.example.figerprintconsole.app.ui.screen.logs.components.Tags
import com.example.figerprintconsole.app.ui.screen.logs.components.UserLogCard
import com.example.figerprintconsole.app.ui.screen.logs.event.LogsScreenUiEvent
import com.example.figerprintconsole.app.ui.screen.logs.state.UserListUiStateLogsScreen

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

        when(val currentUserlistState = uiState.currentUserList) {
            is UserListUiStateLogsScreen.Loading -> {
                AttendanceLoadingScreen() // Loading Screen
            }
            is UserListUiStateLogsScreen.UserList -> {
                if(currentUserlistState.data.isEmpty()) {
                    AttendanceEmptyScreen() // No Data screen
                } else {
                    StaggeredLazyColumn(currentUserlistState.data)
                }
            }
        }
    }
}

@Composable
fun StaggeredLazyColumn(userList: List<AttendanceRecord>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(6.dp)
    ) {
        itemsIndexed(userList, key = { _, log -> log.employeeId }) { index, log ->
            UserLogCard(log = log)
        }
    }
}

@Composable
fun AttendanceLoadingScreen(
    modifier: Modifier = Modifier,
    message: String = "Loading Attendance..."
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Circular progress indicator with gradient circle
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = message,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun AttendanceEmptyScreen(
    modifier: Modifier = Modifier,
    message: String = "No attendance records found."
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null
                )
            }

            // Main message
            Text(
                text = message,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}