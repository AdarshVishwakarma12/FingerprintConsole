package com.example.figerprintconsole.app.ui.screen.users.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.figerprintconsole.app.ui.screen.users.UserScreenViewModel
import com.example.figerprintconsole.app.ui.screen.users.event.UsersUiEvent
import com.example.figerprintconsole.app.ui.screen.users.state.SearchQueryUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListTopBar(
    userScreenViewModel: UserScreenViewModel = hiltViewModel()
) {
    val uiState by userScreenViewModel.uiState.collectAsState()

    TopAppBar(
        title = {
            when(uiState.searchQueryUiState) {
                is SearchQueryUiState.InActive -> {
                    Text(
                        text = "Users",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                is SearchQueryUiState.Active -> Unit
            }
        },
        actions = {
            when(uiState.searchQueryUiState) {
                is SearchQueryUiState.InActive -> {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + expandHorizontally(),
                        exit = fadeOut() + shrinkHorizontally()
                    ) {
                        IconButton(
                            onClick = {
                                userScreenViewModel.onEvent(UsersUiEvent.OpenSearch)
                            }
                        ) {
                            Icon(Icons.Default.Search, "Search")
                        }
                    }
                }
                is SearchQueryUiState.Active -> {
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn() + expandHorizontally(),
                        exit = fadeOut() + shrinkHorizontally()
                    ) {
                        SearchBar(
                            query = (uiState.searchQueryUiState as SearchQueryUiState.Active).searchQuery,
                            onQueryChange = { userScreenViewModel.onEvent(UsersUiEvent.SearchQueryChanged(it)) },
                            onSearch = {},
                            active = true,
                            onActiveChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 8.dp),
                            placeholder = { Text("Search users...") },
                            leadingIcon = {
                                Icon(Icons.Default.Search, "Search")
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        userScreenViewModel.onEvent(UsersUiEvent.CloseSearch)
                                    }
                                ) {
                                    Icon(Icons.Default.Close, "Close search")
                                }
                            }
                        ) { }
                    }
                }
            }
        }
    )
}