package com.bandymoot.fingerprint.app.ui.screen.home.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.DoorFront
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.bandymoot.fingerprint.app.domain.model.DeviceStatusType
import com.bandymoot.fingerprint.app.domain.model.DeviceType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object HomeUtils {

    // Helper functions
    fun getDeviceIcon(type: DeviceType): ImageVector = when (type) {
        DeviceType.TERMINAL -> Icons.Default.PointOfSale
        DeviceType.MOBILE -> Icons.Default.Smartphone
        DeviceType.DESKTOP -> Icons.Default.Computer
        DeviceType.GATE -> Icons.Default.DoorFront
    }

    fun getDeviceColor(status: DeviceStatusType): Color = when (status) {
        DeviceStatusType.ONLINE -> Color(0xFF2E7D32)      // Emerald
        DeviceStatusType.OFFLINE -> Color(0xFF455A64)     // Slate Gray (Neutral)
        DeviceStatusType.MAINTENANCE -> Color(0xFFF9A825) // Amber (Warning)
        DeviceStatusType.ERROR -> Color(0xFFC62828)       // Crimson (Critical)
    }

    fun getHealthColor(score: Int): Color = when {
        score >= 90 -> Color.Green
        score >= 70 -> Color.Yellow
        else -> Color.Red
    }

    fun getHealthStatus(score: Int): String = when {
        score >= 90 -> "Excellent"
        score >= 70 -> "Good"
        else -> "Needs Attention"
    }

    fun formatDateTime(dateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("EEE, MMM d • h:mm a")
        return dateTime.format(formatter)
    }

    fun formatTime(dateTime: LocalDateTime): String {
        val now = LocalDateTime.now()
        return if (dateTime.toLocalDate() == now.toLocalDate()) {
            val formatter = DateTimeFormatter.ofPattern("h:mm a")
            dateTime.format(formatter)
        } else {
            val formatter = DateTimeFormatter.ofPattern("MMM d")
            dateTime.format(formatter)
        }
    }
}