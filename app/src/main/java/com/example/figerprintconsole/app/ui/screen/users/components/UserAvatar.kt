package com.example.figerprintconsole.app.ui.screen.users.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.figerprintconsole.app.domain.model.EnrollmentStatus
import com.example.figerprintconsole.app.ui.screen.users.utils.UserUtils

@Composable
fun UserAvatar(
    userName: String,
    enrollmentStatus: EnrollmentStatus = EnrollmentStatus.NOT_ENROLLED,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.padding(end = 12.dp)
    ) {

//            AsyncImage(
//                model = user.profileImage,
//                contentDescription = "${userName}'s avatar",
//                modifier = Modifier
//                    .size(size)
//                    .clip(CircleShape),
//                contentScale = ContentScale.Crop
//            )

        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(
                    color = UserUtils.getAvatarColor(userName)
                )
        ) {
            Text(
                text = userName.take(2).uppercase(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = (size.value * 0.3).sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Enrollment status indicator dot
        if (enrollmentStatus == EnrollmentStatus.ENROLLED) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    )
                    .align(Alignment.BottomEnd)
            )
        }
    }
}