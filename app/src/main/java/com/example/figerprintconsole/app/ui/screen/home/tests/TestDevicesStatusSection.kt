package com.example.figerprintconsole.app.ui.home.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.figerprintconsole.app.ui.screen.home.components.DevicesStatusSection

@Preview(showBackground = true)
@Composable
fun TestDevicesStatusSection() {
    DevicesStatusSection(
        emptyList(), {}
    )
}