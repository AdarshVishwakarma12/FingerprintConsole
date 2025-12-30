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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListTopBar(
    showSearchBar: Boolean,
    onSearchIconClick: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    searchQuery: String,
) {
    TopAppBar(
        title = {
            if (!showSearchBar) {
                Text(
                    text = "Users",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        },
        actions = {
            // Search bar or icon
            AnimatedVisibility(
                visible = showSearchBar,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally()
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = onSearchQueryChanged,
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
                        IconButton(onClick = onSearchIconClick) {
                            Icon(Icons.Default.Close, "Close search")
                        }
                    }
                ) {}
            }

            if (!showSearchBar) {
                IconButton(onClick = onSearchIconClick) {
                    Icon(Icons.Default.Search, "Search")
                }
            }
        }
    )
}