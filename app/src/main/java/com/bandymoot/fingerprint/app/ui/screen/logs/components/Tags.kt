package com.bandymoot.fingerprint.app.ui.screen.logs.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.domain.model.Device
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import com.bandymoot.fingerprint.app.ui.screen.logs.state.DeviceTag

@Composable
fun Tags(
    deviceList: List<Device>,
    activeTag: DeviceTag,
    onTagSelected: (DeviceTag) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {
            Tag(
                text = "All",
                isActive = activeTag is DeviceTag.All,
                activeColor = Color(0xFF4CAF50),
                showBorder = true,
                onClick = { onTagSelected(DeviceTag.All) }
            )
        }

        items(
            items = deviceList,
            key = { it.deviceCode } // important for stability
        ) { device ->
            val isActive =
                activeTag is DeviceTag.DeviceItem &&
                        activeTag.device.deviceCode == device.deviceCode

            Tag(
                text = device.deviceCode,
                isActive = isActive,
                activeColor = Color(0xFF2196F3),
                showBorder = true,
                onClick = {
                    onTagSelected(DeviceTag.DeviceItem(device))
                }
            )
        }
    }
}

@Composable
fun Tag(
    text: String,
    isActive: Boolean,
    activeColor: Color,
    showBorder: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = if (isActive) activeColor.copy(alpha = 0.15f)
                else Color.Transparent
            )
            .then(
                if (showBorder) {
                    Modifier.border(
                        width = 1.dp,
                        color = activeColor,
                        shape = RoundedCornerShape(20.dp)
                    )
                } else Modifier
            )
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (isActive) activeColor else activeColor,
            fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Medium
        )
    }
}
