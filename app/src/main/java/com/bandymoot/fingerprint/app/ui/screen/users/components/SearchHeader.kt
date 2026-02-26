package com.bandymoot.fingerprint.app.ui.screen.users.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

@Composable
fun SearchHeader(
    query: String,
    onOpenSearch: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    onCloseSearch: () -> Unit,
) {

    ActiveSearchItem(
        query = query,
        onQueryChange = onSearchQueryChanged,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveSearchItem(
    query: String,
    onQueryChange: (String) -> Unit,
) {
    // State to track if the search is focused!!
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    DockedSearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .border(
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.05f),
                shape = RoundedCornerShape(12.dp)
            )
            .onFocusChanged { isFocused = it.isFocused }, // Check for the focused status using Modifier.
        expanded = false, // Replaces 'active'
        onExpandedChange = { },
        // This is the new required "inputField" slot
        inputField = @Composable {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = {
                    // Close keyboard and focus when user hits the "Search button" on keyboard.
                    keyboardController?.hide()
                    focusManager.clearFocus()
                },
                expanded = false,
                onExpandedChange = { },
                placeholder = { Text("Search Users...", style = MaterialTheme.typography.bodyMedium) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp)) },
                trailingIcon = {
                    // Show Cross Signal only if the
                    if(query.isNotEmpty() || isFocused) {
                        IconButton(
                            onClick = {
                                onQueryChange("")
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                }
            )
        },
        shape = RoundedCornerShape(12.dp),
        colors = SearchBarDefaults.colors(
            // A cool, slate-tinted gray (Material Blue Gray 50)
            containerColor = Color(0xFFECEFF1),
            // Ensure the text isn't pure black to keep it soft
            inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = Color(0xFF263238),
                unfocusedTextColor = Color(0xFF455A64)
            )
        )
    ) {
        // Search suggestions go here
    }
}