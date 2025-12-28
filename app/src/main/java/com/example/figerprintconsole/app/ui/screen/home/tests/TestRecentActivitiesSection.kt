package com.example.figerprintconsole.app.ui.screen.home.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.figerprintconsole.app.ui.home.components.RecentActivitiesSection
import com.example.figerprintconsole.app.ui.home.state.ActivityAction
import com.example.figerprintconsole.app.ui.home.state.RecentActivity
import java.time.LocalDateTime

@Preview(showBackground = true)
@Composable
fun TestRecentActivitiesSection() {

    val recentActivity = listOf(
        RecentActivity(
            "00",
            "09",
            "Arvind Kumar",
            ActivityAction.ENROLLMENT,
            LocalDateTime.now(),
            true,
            "1011"
        ),
        RecentActivity(
            "00",
            "08",
            "Afnan Khan",
            ActivityAction.ACCESS_GRANTED,
            LocalDateTime.now(),
            false,
            "1012"
        )
    )

    val recentActivity1 = emptyList<RecentActivity>()

    RecentActivitiesSection(
        activities = recentActivity1,
        onActivityClick = {},
        onViewAllClick = {}
    )
}