package com.bandymoot.fingerprint.app.ui.screen.profile.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bandymoot.fingerprint.app.utils.AppConstant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Settings",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppConstant.TOP_APP_COLOR
        ),
    )
}