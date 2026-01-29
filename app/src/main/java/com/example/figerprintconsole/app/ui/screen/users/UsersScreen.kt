package com.example.figerprintconsole.app.ui.screen.users

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.figerprintconsole.app.domain.model.User
import com.example.figerprintconsole.app.domain.model.UserDetail
import com.example.figerprintconsole.app.ui.screen.users.components.EmptyUsersState
import com.example.figerprintconsole.app.ui.screen.users.components.UserDetailsDialog
import com.example.figerprintconsole.app.ui.screen.users.components.UsersList
import com.example.figerprintconsole.app.ui.screen.users.event.UsersUiEvent
import com.example.figerprintconsole.app.ui.screen.users.state.DetailUserUiState
import com.example.figerprintconsole.app.ui.screen.users.state.SearchQueryUiState
import com.example.figerprintconsole.app.ui.screen.users.state.UsersListState
import com.example.figerprintconsole.app.utils.AppConstant

// Main users list composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListScreen(
    onEnrollUser: (User?) -> Unit,
    navigateToAttendanceScreen: (UserDetail) -> Unit,
    modifier: Modifier = Modifier,
    userScreenViewModel: UserScreenViewModel = hiltViewModel()
) {
    val uiState by userScreenViewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),

        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onEnrollUser(null) },
                icon = { Icon(Icons.Default.Add, "Add User") },
                text = { Text("Add User") },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when(uiState.listState) {
                is UsersListState.Loading -> { }
                is UsersListState.Success -> {
                    if((uiState.listState as UsersListState.Success).users.isEmpty()) {
                        EmptyUsersState(
                            searchQuery = (uiState.searchQueryUiState as? SearchQueryUiState.Active)?.searchQuery.orEmpty(),
                            onAddUserClick = { onEnrollUser(null) }
                        )
                    } else {
                        UsersList(
                            users = (uiState.listState as UsersListState.Success).users,
                            onUserClick = { user -> userScreenViewModel.onEvent(UsersUiEvent.OpenUserDetail(user)) },
                            onEnrollUser = onEnrollUser,
                            onDeleteUser = { },
                            modifier = modifier
                        )
                    }
                }
                is UsersListState.Error -> {
                    // Update in SnackBarHostState / Manager
                    // Through viewModel!
                }
            }
        }

        when (uiState.detailUserUiState) {
            is DetailUserUiState.Hidden -> {}
            is DetailUserUiState.Loading -> {
                // Show Loading State!
                LoadingAlertDialog(
                    onDismissRequest = {
                        userScreenViewModel.onEvent(UsersUiEvent.CloseUserDetail)
                    }
                )
            }

            is DetailUserUiState.Success -> {
                AppConstant.debugMessage("I am active! And want to render!!")
                UserDetailsDialog(
                    userDetail = (uiState.detailUserUiState as DetailUserUiState.Success).detailUser,
                    onDismiss = {
                        userScreenViewModel.onEvent(UsersUiEvent.CloseUserDetail)
                    },
                    onEnroll = { onEnrollUser(null) },
                    navigateToAttendance = navigateToAttendanceScreen
                )
            }

            is DetailUserUiState.Error -> {}
        }
    }
}

@Composable
fun LoadingAlertDialog(
    message: String = "Loading…",
    onDismissRequest: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest?.invoke()
        },
        confirmButton = {},
        dismissButton = {},
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 24.dp)
            ) {
                CircularProgressIndicator()
                Text(text = message)
            }
        }
    )
}