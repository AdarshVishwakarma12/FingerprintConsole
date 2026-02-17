package com.bandymoot.fingerprint.app.ui.screen.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bandymoot.fingerprint.R
import com.bandymoot.fingerprint.app.ui.screen.profile.event.ProfileScreenUiEvent
import com.bandymoot.fingerprint.app.ui.screen.profile.state.ProfileScreenUiState

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileContent(
        state = uiState,
        onLogoutClick = {
            viewModel.onEvent(ProfileScreenUiEvent.LOGOUT)
        },
        onOrganizationClick = { /* TODO */ },
        onDeviceClick = { /* TODO */ },
        onChangePasswordClick = { /* TODO */ },
        onAboutClick = { /* TODO */ },
        onFaqClick = { /* TODO */ },
        onFeedbackClick = { /* TODO */ }
    )
}

@Composable
private fun ProfileContent(
    state: ProfileScreenUiState,
    onLogoutClick: () -> Unit,
    onOrganizationClick: () -> Unit,
    onDeviceClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onAboutClick: () -> Unit,
    onFaqClick: () -> Unit,
    onFeedbackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {

        ProfileHeader(
            name = state.managerName,
            email = state.managerEmail
        )

        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Personal Information")
        ProfileItem("Organization", onOrganizationClick)
        ProfileItem("Device", onDeviceClick)

        Spacer(modifier = Modifier.height(16.dp))

        SectionTitle("Security")
        ProfileItem("Change Password", onChangePasswordClick)

        Spacer(modifier = Modifier.height(16.dp))

        SectionTitle("Help and Support")
        ProfileItem("About YOUR COMPANY", onAboutClick)
        ProfileItem("Frequently Asked Questions", onFaqClick)
        ProfileItem("Submit Feedback", onFeedbackClick)

        Spacer(modifier = Modifier.height(32.dp))

        LogoutButton(onLogoutClick)
    }
}

@Composable
private fun ProfileHeader(
    name: String,
    email: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_profile_placeholder),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .semantics { contentDescription = "User profile image" },
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun ProfileItem(
    title: String,
    onClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "$title navigation",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Divider(color = MaterialTheme.colorScheme.surfaceVariant)
    }
}

@Composable
private fun LogoutButton(
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.error
        ),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.error
        )
    ) {
        Icon(Icons.Default.Logout, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Sign out")
    }
}