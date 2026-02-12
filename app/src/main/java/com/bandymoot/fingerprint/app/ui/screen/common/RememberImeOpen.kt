package com.bandymoot.fingerprint.app.ui.screen.common

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

// THIS IS THE WORST CODE EVER!
// BUT IT WORKS
@Composable
fun rememberImeOpen(): Boolean {
    val density = LocalDensity.current
    val imeBottom = WindowInsets.ime.getBottom(density)

    return imeBottom > with(density) { 100.dp.toPx() }
}
