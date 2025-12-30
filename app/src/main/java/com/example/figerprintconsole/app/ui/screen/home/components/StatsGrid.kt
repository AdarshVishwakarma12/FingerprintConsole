package com.example.figerprintconsole.app.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.figerprintconsole.app.domain.model.DashboardStats
import com.example.figerprintconsole.app.domain.model.StatItem

@Composable
fun StatsGrid(
    stats: DashboardStats,
    isLargeScreen: Boolean
) {

    val colorScheme = MaterialTheme.colorScheme

    val statsItems = remember(stats) {
        listOf(
            StatItem(
                title = "Total Users",
                value = stats.totalUsers.toString(),
                icon = Icons.Default.People,
                color = colorScheme.tertiary,
                trend = "+12%",
                subtitle = "${stats.enrolledUsers} enrolled"
            ),
            StatItem(
                title = "Recent Scans",
                value = stats.recentScans.toString(),
                icon = Icons.Default.Fingerprint,
                color = colorScheme.tertiary,
                trend = "+24%",
                subtitle = "Last 24h"
            ),
            StatItem(
                title = "Success Rate",
                value = "${(stats.successRate * 100).toInt()}%",
                icon = Icons.Default.TrendingUp,
                color = colorScheme.tertiary,
                trend = "+3.2%",
                subtitle = "Accuracy"
            ),
            StatItem(
                title = "Active Devices",
                value = stats.activeDevices.toString(),
                icon = Icons.Default.Devices,
                color = colorScheme.tertiary,
                trend = if (isLargeScreen) "2 online" else null,
                subtitle = "${stats.pendingEnrollments} pending"
            )
        )
    }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(statsItems) { stat ->
            StatCard(stat = stat)
        }
    }
}
