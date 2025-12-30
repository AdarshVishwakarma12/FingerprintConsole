package com.example.figerprintconsole.app.ui.home.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.figerprintconsole.app.domain.model.DeviceStatus
import com.example.figerprintconsole.app.ui.home.components.DeviceStatusCard
import com.example.figerprintconsole.app.domain.model.DeviceStatusType
import com.example.figerprintconsole.app.domain.model.DeviceType
import java.time.LocalDateTime

@Preview(showBackground = true)
@Composable
fun TestDeviceStatusCard() {

    val deviceStatus = DeviceStatus(
        "000",
        "Arvind Kumar",
        DeviceType.TERMINAL,
        DeviceStatusType.OFFLINE,
        LocalDateTime.now(),
        300,
        "Delhi, India"
    )

    DeviceStatusCard(
        deviceStatus,
        {}
    )
}