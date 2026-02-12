package com.bandymoot.fingerprint.app.ui.screen.enroll_user.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentState

@Composable
fun EnrollmentActionTwo(
    uiState: EnrollmentState,
    onRetry: () -> Unit,
    onComplete: () -> Unit
) {

    // Handle Action Two and Three Collectively
    /* The animation(Just Add a text for now) + Update State + delay(5000) / Wait Infinite(with timeout) + Check if enrolled Success */

    // We may experience some crash if we rotate the screen for now / or Change tHEME!!
    // I need structure like hell!!!(something i've never done before)
    // This business logic inside UI is dangerous!, Kindly move to viewModel!!!

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CircularProgressIndicator()
    }
}