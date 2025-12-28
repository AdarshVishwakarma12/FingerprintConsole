package com.example.figerprintconsole.app.ui.users.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.figerprintconsole.app.ui.users.state.EnrollmentStatus
import com.example.figerprintconsole.app.ui.users.state.User
import java.time.LocalDateTime
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.figerprintconsole.app.ui.users.utils.UserUtils

@Composable
fun ProfileImage(
    user: User,
    size: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
    ) {
        if (user.profileImage != null) {
            AsyncImage(
                model = user.profileImage,
                contentDescription = "${user.name}'s avatar",
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            // Fallback to initials avatar
            val backgroundColorForUser = UserUtils.getAvatarColor(user.name)
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(
                        color = backgroundColorForUser
                    )
            ) {
                Text(
                    text = user.name.take(2).uppercase(),
                    color = contentColorFor(backgroundColorForUser),
                    fontWeight = FontWeight.Bold,
                    fontSize = (size.value * 0.3).sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestProfileImage() {
    val user = User(
        id ="1",
        name = "Afnan Khan",
        email = "afnan@bandymoot.com",
        profileImage = null,
        enrollmentStatus = EnrollmentStatus.ENROLLED,
        lastAccess = LocalDateTime.now(),
        fingerprintCount = 0
    )

    ProfileImage(user, size = 50.dp)
}