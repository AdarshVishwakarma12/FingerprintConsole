package com.example.figerprintconsole.app.ui.screen.users.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.figerprintconsole.app.ui.screen.users.EnrollmentStatus

@Composable
fun Tags(
    state: EnrollmentStatus,
    compact: Boolean = false
) {

    val (text, color, icon) = when (state) {
        EnrollmentStatus.ENROLLED -> Triple(
            if (compact) "" else "Enrolled",
            Color.Green,
            Icons.Default.CheckCircle
        )
        EnrollmentStatus.PENDING -> Triple(
            if (compact) "!" else "Pending",
            Color.Yellow,
            Icons.Default.Pending
        )
        EnrollmentStatus.NOT_ENROLLED -> Triple(
            if (compact) "!" else "Not Enrolled",
            Color.Red,
            Icons.Default.Warning
        )
        EnrollmentStatus.EXPIRED -> Triple(
            if (compact) "!" else "Expired",
            Color.Magenta,
            Icons.Default.Cancel
        )
    }

    Box(
        modifier = Modifier
            .background(
                color.copy(alpha = .5f),
                shape = RoundedCornerShape(4.dp),
            )
            .border(
                .9.dp,
                color,
                RoundedCornerShape(4.dp)
            )
            .padding(8.dp, 4.dp)
    ) {

        Row(
            modifier = Modifier.align(Alignment.Center),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier.align(Alignment.CenterVertically).size(15.dp),
            )
            Spacer(Modifier.padding(2.dp))
            Text(
                text = text,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TestTags() {
    Tags(state = EnrollmentStatus.ENROLLED)
}