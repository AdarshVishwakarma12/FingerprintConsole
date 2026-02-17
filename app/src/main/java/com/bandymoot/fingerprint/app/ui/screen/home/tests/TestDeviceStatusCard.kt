package com.bandymoot.fingerprint.app.ui.screen.home.tests

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bandymoot.fingerprint.app.domain.model.Device
import com.bandymoot.fingerprint.app.ui.home.components.DeviceStatusCard
import com.bandymoot.fingerprint.app.domain.model.DeviceStatusType
import com.bandymoot.fingerprint.app.domain.model.DeviceType

@Preview(showBackground = true)
@Composable
fun TestDeviceStatusCard() {

    val deviceStatus = Device(
        "",
        "000",
        "Arvind Kumar",
        null,
        "ONLINE",
        "Yesterday",
        90,
        "20 JAN",
        DeviceType.TERMINAL,
        DeviceStatusType.OFFLINE,
        "abc",
        "123",
        "1234",
        "1289"
    )

    DeviceStatusCard(
        deviceStatus,
        {}
    )
}