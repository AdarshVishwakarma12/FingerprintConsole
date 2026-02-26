package com.bandymoot.fingerprint.app.ui.screen.users.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.domain.model.User
import com.bandymoot.fingerprint.app.ui.screen.users.state.SearchQueryUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersList(
    users: List<User>,
    onUserClick: (User) -> Unit,
    onEnrollUser: (User) -> Unit,
    onDeleteUser: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
         verticalArrangement = Arrangement.spacedBy(8.dp),
         contentPadding = PaddingValues(6.dp)
    ) {

        items(users, key = { it.uniqueServerId }) { user ->
            UserListItem(
                user = user,
                onClick = { onUserClick(user) },
                onEnroll = { onEnrollUser(user) },
                onDelete = { onDeleteUser(user) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}