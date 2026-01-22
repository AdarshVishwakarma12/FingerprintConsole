package com.example.figerprintconsole.app.ui.screen.users

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.figerprintconsole.app.domain.model.User
import com.example.figerprintconsole.app.ui.screen.users.components.EmptyUsersState
import com.example.figerprintconsole.app.ui.screen.users.components.LoadingUsers
import com.example.figerprintconsole.app.ui.screen.users.components.UserDetailsDialog
import com.example.figerprintconsole.app.ui.screen.users.components.UsersList
import com.example.figerprintconsole.app.ui.screen.users.components.UsersListTopBar
import com.example.figerprintconsole.app.ui.screen.users.event.UsersScreenEvent
import com.example.figerprintconsole.app.ui.screen.users.state.UserDetailUiState
import com.example.figerprintconsole.app.ui.screen.users.state.UsersUiState

// Main users list composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListScreen(
    onEnrollUser: (User) -> Unit,
    onEnrollNewUser: () -> Unit,
    modifier: Modifier = Modifier,
    userScreenViewModel: UserScreenViewModel = hiltViewModel()
) {
    var showSearchBar by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val allUsers by userScreenViewModel.uiStateAllUsers.collectAsState()
    val userDetailState by userScreenViewModel.uiStateUserById.collectAsState()

    // Prevent this from loading again and again!!
    LaunchedEffect(Unit) { userScreenViewModel.onEvent(UsersScreenEvent.GetAllUsers) }

    // The Skeleton
    // Render ::
    // -> Top Bar
    // -> Floating Window
    // -> Check for "allUsers" state
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            UsersListTopBar(
                showSearchBar = showSearchBar,
                onSearchIconClick = { showSearchBar = !showSearchBar },
                onSearchQueryChanged = { it -> searchQuery = it },
                searchQuery = searchQuery
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onEnrollNewUser() },
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
            when(allUsers) {
                is UsersUiState.Loading -> {
                    LoadingUsers()
                }
                is UsersUiState.Failure -> {
                    // Error State!!

                }
                is UsersUiState.Success -> {
                    val users = (allUsers as UsersUiState.Success).users

                    if(users.isEmpty()) {
                        EmptyUsersState(
                            searchQuery = searchQuery,
                            onAddUserClick = { onEnrollNewUser() }
                        )
                    } else {
                        UsersList(
                            users = users,
                            onUserClick = {
                                user -> selectedUser = user
                                print("You are calling perfectly...")
                                          },
                            onEnrollUser = onEnrollUser,
                            onDeleteUser = { },
                            modifier = modifier
                        )
                    }
                }
            }
        }

        // User details dialog
        selectedUser?.let { user ->

//            // Wait till we get the result..
//            if (userDetailState is UserDetailUiState.Success && (userDetailState as UserDetailUiState.Success).user.fingerprintId == user.fingerprintId) {
//                val userInfo = (userDetailState as UserDetailUiState.Success).user
//
//                UserDetailsDialog(
//                    user = userInfo,
//                    onDismiss = { selectedUser = null },
//                    onEnroll = {
//                        onEnrollUser(userInfo)
//                        selectedUser = null
//                    },
//                    onDelete = {
//                        selectedUser = null
//                    }
//                )
//            }
        }
    }
}