package com.bandymoot.fingerprint.app.ui.screen.enroll.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.ui.screen.enroll.state.EnrollmentScreenState
import com.bandymoot.fingerprint.app.ui.screen.enroll.state.EnrollmentState
import kotlinx.coroutines.delay

@Composable
fun EnrollmentActionTwo(
    uiState: EnrollmentScreenState,
    state: EnrollmentState,
    onRetry: () -> Unit,
    onComplete: () -> Unit
) {

    // Handle Action Two and Three Collectively
    /* The animation(Just Add a text for now) + Update State + delay(5000) / Wait Infinite(with timeout) + Check if enrolled Success */

    // We may experience some crash if we rotate the screen for now / or Change tHEME!!
    // I need structure like hell!!!(something i've never done before)
    // This business logic inside UI is dangerous!, Kindly move to viewModel!!!
    LaunchedEffect(state) {
        if(state.currentStep == 2) {
            delay(4000)
            onComplete()
        }
        // At step 3 check from the websocket, if we get enrolled response!!
        if(state.currentStep == 3) {
            delay(5000)
            onComplete()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CircularProgressIndicator()
    }
}