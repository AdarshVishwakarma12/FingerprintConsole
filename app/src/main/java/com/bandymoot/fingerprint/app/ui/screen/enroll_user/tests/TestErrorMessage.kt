package com.bandymoot.fingerprint.app.ui.screen.enroll_user.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bandymoot.fingerprint.app.ui.enroll.components.ErrorMessage

@Preview(showBackground = true)
@Composable
fun TestErrorMessage() {
    ErrorMessage(
        "WebSocket Error"
    )
}