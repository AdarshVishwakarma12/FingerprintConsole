package com.example.figerprintconsole.app.ui.home.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.figerprintconsole.app.ui.home.components.UserMenuDialog

@Preview(showBackground = true)
@Composable
fun TestUserMenuDialog() {
    UserMenuDialog({}, {}, {})
}