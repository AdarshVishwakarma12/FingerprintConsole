package com.bandymoot.fingerprint.app.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.ui.screen.users.utils.UserUtils
import com.bandymoot.fingerprint.app.utils.AppConstant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar(
    userName: String = "Arvind",
    showNotifications: Boolean = false,
    onNotificationsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSearch: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    CenterAlignedTopAppBar(
        title = {
        },
        actions = {
            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = {},
                active = false,
                onActiveChange = {},
                modifier = Modifier
                    .widthIn(max = 300.dp)
                    .padding(start = 4.dp, end = 4.dp, bottom = 8.dp),
                placeholder = { Text("Search users, devices...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, "Search")
                }
            ) {}

            // Notifications
            IconButton(onClick = onNotificationsClick) {
                BadgedBox(
                    badge = {
                        Badge(
                            containerColor = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(8.dp)
                        )
                    }
                ) {
                    Icon(
                        imageVector = if (showNotifications)
                            Icons.Default.Notifications
                        else Icons.Outlined.Notifications,
                        contentDescription = "Notifications"
                    )
                }
            }

            // Profile
            IconButton(onClick = onProfileClick) {
                val backgroundProfileColor = UserUtils.getAvatarColor(userName)
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color = backgroundProfileColor)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = userName.take(2).uppercase(), // change
                        color = UserUtils.contentColorFor(backgroundProfileColor),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppConstant.TOP_APP_COLOR
        ),
    )
}