package com.example.figerprintconsole.app.ui.screen.users.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.figerprintconsole.app.domain.model.User

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
        items(users, key = { it.employeeCode }) { user ->
            UserListItem(
                user = user,
                onClick = { onUserClick(user) },
                onEnroll = { onEnrollUser(user) },
                onDelete = { onDeleteUser(user) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}