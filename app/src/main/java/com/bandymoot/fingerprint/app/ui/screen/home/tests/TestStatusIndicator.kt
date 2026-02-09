package com.bandymoot.fingerprint.app.ui.home.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.bandymoot.fingerprint.app.ui.home.components.StatusIndicator

@Preview(showBackground = true)
@Composable
fun TestStatusIndicator() {

    StatusIndicator(
        Color.Yellow,
        "Help Afnan"
    )
}