package com.example.figerprintconsole.app.ui.home.state

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDateTime

data class DashboardStats(
    val totalUsers: Int = 0,
    val enrolledUsers: Int = 0,
    val recentScans: Int = 0,
    val successRate: Double = 0.0,
    val activeDevices: Int = 0,
    val pendingEnrollments: Int = 0
)

data class RecentActivity(
    val id: String,
    val userId: String,
    val userName: String,
    val action: ActivityAction,
    val timestamp: LocalDateTime,
    val success: Boolean,
    val deviceId: String? = null
)

enum class ActivityAction {
    ENROLLMENT,
    VERIFICATION,
    ACCESS_GRANTED,
    ACCESS_DENIED,
    DEVICE_ADDED,
    USER_ADDED
}

data class QuickAction(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val route: String
)

data class DeviceStatus(
    val id: String,
    val name: String,
    val type: DeviceType,
    val status: DeviceStatusType,
    val lastActive: LocalDateTime,
    val connectedUsers: Int,
    val location: String? = null
)

enum class DeviceType {
    TERMINAL,
    MOBILE,
    DESKTOP,
    GATE
}

enum class DeviceStatusType {
    ONLINE,
    OFFLINE,
    MAINTENANCE,
    ERROR
}

// === HELPER COMPOSABLES & FUNCTIONS ===
data class StatItem(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val color: Color,
    val trend: String?,
    val subtitle: String?
)

data class BottomNavItem(
    val label: String,
    val icon: ImageVector
)
