package com.example.figerprintconsole.app.ui.screen.enroll.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentState
import com.example.figerprintconsole.app.utils.AppConstant
import kotlinx.coroutines.delay

@Composable
fun EnrollmentActionTwo(
    state: EnrollmentState,
    onRetry: () -> Unit,
    onComplete: () -> Unit
) {

    // Handle Action Two and Three Collectively
    /* The animation(Just Add a text for now) + Update State + delay(5000) / Wait Infinite(with timeout) + Check if enrolled Success */

    // We may experience some crash if we rotate the screen for now!!
    // I need structure like hell!!!(something i've never done before)
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