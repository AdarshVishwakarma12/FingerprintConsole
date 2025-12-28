package com.example.figerprintconsole.app.ui.enroll.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.figerprintconsole.app.ui.enroll.components.FingerprintPattern

@Preview(showBackground = true)
@Composable
fun TestFingerprintPattern() {
    FingerprintPattern(true, 0f)
}