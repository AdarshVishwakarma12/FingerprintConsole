package com.example.figerprintconsole.app.ui.home.tests

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.material.icons.filled.Groups
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.figerprintconsole.app.ui.home.components.QuickActionsSection
import com.example.figerprintconsole.app.ui.home.state.QuickAction

@Preview(showBackground = true)
@Composable
fun TestQuickActionsSection() {

    val actions = listOf(
        QuickAction(
            "001",
            "Enroll",
            "Enroll new user",
            Icons.Default.DeviceHub,
            Color.Blue,
            ""
        ),
        QuickAction(
            "002",
            "Delete User",
            "Delete user",
            Icons.Default.Cancel,
            Color.Red,
            ""
        ),
        QuickAction(
            "003",
            "Grant Access",
            "Grant Access to User",
            Icons.Default.Groups,
            Color.DarkGray,
            ""
        )
    )

    QuickActionsSection(
        actions,
        {}
    )
}