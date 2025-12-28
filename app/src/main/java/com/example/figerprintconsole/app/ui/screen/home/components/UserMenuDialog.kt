package com.example.figerprintconsole.app.ui.home.components

import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserMenuDialog(
    onDismiss: () -> Unit,
    onLogout: () -> Unit,
    onSettings: () -> Unit
) {
    DropdownMenu(
        expanded = true,
        onDismissRequest = onDismiss,
        modifier = Modifier.width(200.dp)
    ) {
        DropdownMenuItem(
            text = { Text("Profile") },
            onClick = { /* Navigate to profile */ },
            leadingIcon = { Icon(Icons.Default.Person, null) }
        )
        DropdownMenuItem(
            text = { Text("Settings") },
            onClick = onSettings,
            leadingIcon = { Icon(Icons.Default.Settings, null) }
        )
        Divider()
        DropdownMenuItem(
            text = { Text("Logout", color = MaterialTheme.colorScheme.error) },
            onClick = onLogout,
            leadingIcon = {
                Icon(
                    Icons.Default.Logout,
                    null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        )
    }
}