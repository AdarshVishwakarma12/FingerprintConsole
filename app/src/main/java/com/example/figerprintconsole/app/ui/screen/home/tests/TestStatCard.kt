package com.example.figerprintconsole.app.ui.home.tests

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.figerprintconsole.app.domain.model.StatItem
import com.example.figerprintconsole.app.ui.home.components.StatCard

@Preview(showBackground = true)
@Composable
fun TestStatCard() {
    val statItem = StatItem(
        title = "Total Users",
        value = "1000",
        icon = Icons.Default.People,
        color = MaterialTheme.colorScheme.primary,
        trend = "+12%",
        subtitle = "${100} enrolled"
    )

    StatCard(statItem)
}