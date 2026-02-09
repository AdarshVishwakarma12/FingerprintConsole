package com.bandymoot.fingerprint.app.ui.home.tests

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeviceHub
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.bandymoot.fingerprint.app.domain.model.QuickAction
import com.bandymoot.fingerprint.app.ui.home.components.QuickActionCard

@Preview(showBackground = true)
@Composable
fun TestQuickActionCard() {

    val action = QuickAction(
        "001",
        "Enroll",
        "Enroll new user",
        Icons.Default.DeviceHub,
        Color.Blue,
        ""
    )

    QuickActionCard(
        action,
        {}
    )
}