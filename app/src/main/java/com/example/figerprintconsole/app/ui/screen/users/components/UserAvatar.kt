package com.example.figerprintconsole.app.ui.screen.users.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.figerprintconsole.app.domain.model.EnrollmentStatus
import com.example.figerprintconsole.app.domain.model.User
import com.example.figerprintconsole.app.ui.screen.users.utils.UserUtils

@Composable
fun UserAvatar(
    user: User,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        if (user.profileImage != null) {
            AsyncImage(
                model = user.profileImage,
                contentDescription = "${user.fullName}'s avatar",
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            // Fallback to initials avatar
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(
                        color = UserUtils.getAvatarColor(user.fullName)
                    )
            ) {
                Text(
                    text = user.fullName.take(2).uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = (size.value * 0.3).sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        // Enrollment status indicator dot
        if (user.enrollmentStatus == EnrollmentStatus.ENROLLED) {
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