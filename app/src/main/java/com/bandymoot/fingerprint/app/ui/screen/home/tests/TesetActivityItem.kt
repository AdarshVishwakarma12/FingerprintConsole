package com.bandymoot.fingerprint.app.ui.home.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bandymoot.fingerprint.app.domain.model.RecentActivity
import com.bandymoot.fingerprint.app.ui.home.components.ActivityItem
import com.bandymoot.fingerprint.app.domain.model.ActivityAction
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