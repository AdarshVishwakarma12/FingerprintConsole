package com.bandymoot.fingerprint.app.ui.home.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bandymoot.fingerprint.app.ui.home.components.StatsGrid
import com.bandymoot.fingerprint.app.domain.model.DashboardStats

@Preview(showBackground = true)
@Composable
fun TestStatsGrid() {

    val dashboardStats = DashboardStats()
    StatsGrid(
        dashboardStats,
        false
    )
}