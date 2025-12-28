package com.example.figerprintconsole.app.ui.home.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.figerprintconsole.app.ui.home.state.RecentActivity
import com.example.figerprintconsole.app.ui.home.components.ActivityItem
import com.example.figerprintconsole.app.ui.home.state.ActivityAction
import java.time.LocalDateTime

@Preview(showBackground = true)
@Composable
fun TestActivityItem() {

    val recentActivity = RecentActivity(
        "00",
        "09",
        "Arvind Kumar",
        ActivityAction.ENROLLMENT,
        LocalDateTime.now(),
        false,
        "1011"
    )

    ActivityItem(
        recentActivity,
        {}
    )
}