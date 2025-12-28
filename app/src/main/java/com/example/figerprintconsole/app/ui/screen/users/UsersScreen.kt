package com.example.figerprintconsole.app.ui.screen.users

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp


// User data model
data class User(
    val id: String,
    val name: String,
    val email: String,
    val profileImage: String? = null,
    val enrollmentStatus: EnrollmentStatus,
    val lastAccess: LocalDateTime? = null,
    val fingerprintCount: Int = 0
)

enum class EnrollmentStatus {
    ENROLLED,
    PENDING,
    NOT_ENROLLED,
    EXPIRED
}

// List display modes
enum class UserListDisplayMode {
    GRID,
    LIST,
    COMPACT
}

// Main users list composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersListScreen(
    users: List<User>,
    onUserClick: (User) -> Unit,
    onEnrollUser: (User) -> Unit,
    onDeleteUser: (User) -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    displayMode: UserListDisplayMode = UserListDisplayMode.LIST,
    isLoading: Boolean = false,
    searchQuery: String = ""
) {
    var showSearchBar by remember { mutableStateOf(false) }
    var selectedUser by remember { mutableStateOf<User?>(null) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            UsersListTopBar(
                showSearchBar = showSearchBar,
                onSearchIconClick = { showSearchBar = !showSearchBar },
                onSearchQueryChanged = onSearchQueryChanged,
                searchQuery = searchQuery,
                displayMode = displayMode,
                onDisplayModeChange = { /* Handle mode change */ }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* Handle add new user */ },
                icon = { Icon(Icons.Default.Add, "Add User") },
                text = { Text("Add User") },
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    LoadingUsers()
                }
                users.isEmpty() -> {
                    EmptyUsersState(
                        searchQuery = searchQuery,
                        onAddUserClick = { /* Handle add */ }
                    )
                }
                else -> {
                    UsersListContent(
                        users = users,
                        displayMode = displayMode,
                        onUserClick = onUserClick,
                        onEnrollUser = onEnrollUser,
                        onDeleteUser = onDeleteUser,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        // User details dialog
        selectedUser?.let { user ->
            UserDetailsDialog(
                user = user,
                onDismiss = { selectedUser = null },
                onEnroll = {
                    onEnrollUser(user)
                    selectedUser = null
                },
                onDelete = {
                    onDeleteUser(user)
                    selectedUser = null
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UsersListTopBar(
    showSearchBar: Boolean,
    onSearchIconClick: () -> Unit,
    onSearchQueryChanged: (String) -> Unit,
    searchQuery: String,
    displayMode: UserListDisplayMode,
    onDisplayModeChange: (UserListDisplayMode) -> Unit
) {
    TopAppBar(
        title = {
            if (!showSearchBar) {
                Text(
                    text = "Users",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        },
        actions = {
            // Search bar or icon
            AnimatedVisibility(
                visible = showSearchBar,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally()
            ) {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = onSearchQueryChanged,
                    onSearch = {},
                    active = true,
                    onActiveChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp),
                    placeholder = { Text("Search users...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, "Search")
                    },
                    trailingIcon = {
                        IconButton(onClick = onSearchIconClick) {
                            Icon(Icons.Default.Close, "Close search")
                        }
                    }
                ) {}
            }

            if (!showSearchBar) {
                IconButton(onClick = onSearchIconClick) {
                    Icon(Icons.Default.Search, "Search")
                }

                // Display mode selector
                DisplayModeMenu(
                    currentMode = displayMode,
                    onModeSelected = onDisplayModeChange
                )
            }
        }
    )
}

@Composable
private fun DisplayModeMenu(
    currentMode: UserListDisplayMode,
    onModeSelected: (UserListDisplayMode) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = when (currentMode) {
                    UserListDisplayMode.GRID -> Icons.Default.GridView
                    UserListDisplayMode.LIST -> Icons.Default.List
                    UserListDisplayMode.COMPACT -> Icons.Default.ViewDay
                },
                contentDescription = "Change view"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Grid View") },
                onClick = {
                    onModeSelected(UserListDisplayMode.GRID)
                    expanded = false
                },
                leadingIcon = { Icon(Icons.Default.GridView, null) }
            )
            DropdownMenuItem(
                text = { Text("List View") },
                onClick = {
                    onModeSelected(UserListDisplayMode.LIST)
                    expanded = false
                },
                leadingIcon = { Icon(Icons.Default.List, null) }
            )
            DropdownMenuItem(
                text = { Text("Compact View") },
                onClick = {
                    onModeSelected(UserListDisplayMode.COMPACT)
                    expanded = false
                },
                leadingIcon = { Icon(Icons.Default.ViewDay, null) }
            )
        }
    }
}

@Composable
private fun UsersListContent(
    users: List<User>,
    displayMode: UserListDisplayMode,
    onUserClick: (User) -> Unit,
    onEnrollUser: (User) -> Unit,
    onDeleteUser: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    when (displayMode) {
        UserListDisplayMode.GRID -> {
            UsersGrid(
                users = users,
                onUserClick = onUserClick,
                onEnrollUser = onEnrollUser,
                onDeleteUser = onDeleteUser,
                modifier = modifier
            )
        }
        UserListDisplayMode.LIST -> {
            UsersVerticalList(
                users = users,
                onUserClick = onUserClick,
                onEnrollUser = onEnrollUser,
                onDeleteUser = onDeleteUser,
                modifier = modifier
            )
        }
        UserListDisplayMode.COMPACT -> {
            UsersCompactList(
                users = users,
                onUserClick = onUserClick,
                onEnrollUser = onEnrollUser,
                onDeleteUser = onDeleteUser,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun UsersVerticalList(
    users: List<User>,
    onUserClick: (User) -> Unit,
    onEnrollUser: (User) -> Unit,
    onDeleteUser: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(6.dp)
    ) {
        items(users, key = { it.id }) { user ->
            UserListItem(
                user = user,
                onClick = { onUserClick(user) },
                onEnroll = { onEnrollUser(user) },
                onDelete = { onDeleteUser(user) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun UserListItem(
    user: User,
    onClick: () -> Unit,
    onEnroll: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile image/avatar
            UserAvatar(
                user = user,
                size = 56.dp,
                modifier = Modifier.padding(end = 16.dp)
            )

            // User info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    EnrollmentStatusBadge(status = user.enrollmentStatus)
                }

                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Last access
                    user.lastAccess?.let {
                        Text(
                            text = "Last access: ${formatDate(it)}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Fingerprint count
                    if (user.fingerprintCount > 0) {
                        Text(
                            text = "${user.fingerprintCount} prints",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
        // Action buttons
        UserActions(
            enrollmentStatus = user.enrollmentStatus,
            onEnroll = onEnroll,
            onDelete = onDelete,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
private fun UsersGrid(
    users: List<User>,
    onUserClick: (User) -> Unit,
    onEnrollUser: (User) -> Unit,
    onDeleteUser: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(users, key = { it.id }) { user ->
            UserGridItem(
                user = user,
                onClick = { onUserClick(user) },
                onEnroll = { onEnrollUser(user) },
                onDelete = { onDeleteUser(user) }
            )
        }
    }
}

@Composable
private fun UserGridItem(
    user: User,
    onClick: () -> Unit,
    onEnroll: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserAvatar(user = user, size = 80.dp)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = user.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            EnrollmentStatusBadge(status = user.enrollmentStatus)

            Spacer(modifier = Modifier.height(8.dp))

            // Quick actions for grid view
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = onEnroll,
                    modifier = Modifier.size(36.dp),
                    enabled = user.enrollmentStatus != EnrollmentStatus.ENROLLED
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = "Enroll fingerprint",
                        tint = if (user.enrollmentStatus != EnrollmentStatus.ENROLLED)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete user",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun UsersCompactList(
    users: List<User>,
    onUserClick: (User) -> Unit,
    onEnrollUser: (User) -> Unit,
    onDeleteUser: (User) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        items(users, key = { it.id }) { user ->
            CompactUserItem(
                user = user,
                onClick = { onUserClick(user) },
                onEnroll = { onEnrollUser(user) },
                onDelete = { onDeleteUser(user) }
            )
        }
    }
}

@Composable
private fun CompactUserItem(
    user: User,
    onClick: () -> Unit,
    onEnroll: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserAvatar(user = user, size = 40.dp)

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = user.email,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            EnrollmentStatusBadge(
                status = user.enrollmentStatus,
                compact = true
            )
        }
    }
}

@Composable
private fun UserAvatar(
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
                contentDescription = "${user.name}'s avatar",
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
                        color = getAvatarColor(user.name)
                    )
            ) {
                Text(
                    text = user.name.take(2).uppercase(),
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

@Composable
private fun EnrollmentStatusBadge(
    status: EnrollmentStatus,
    compact: Boolean = false
) {
    val (text, color, icon) = when (status) {
        EnrollmentStatus.ENROLLED -> Triple(
            if (compact) "" else "Enrolled",
            MaterialTheme.colorScheme.primaryContainer,
            Icons.Default.CheckCircle
        )
        EnrollmentStatus.PENDING -> Triple(
            if (compact) "!" else "Pending",
            MaterialTheme.colorScheme.tertiaryContainer,
            Icons.Default.Pending
        )
        EnrollmentStatus.NOT_ENROLLED -> Triple(
            if (compact) "!" else "Not Enrolled",
            MaterialTheme.colorScheme.errorContainer,
            Icons.Default.Warning
        )
        EnrollmentStatus.EXPIRED -> Triple(
            if (compact) "!" else "Expired",
            MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.8f),
            Icons.Default.Cancel
        )
    }

    if (compact && status != EnrollmentStatus.ENROLLED) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    } else if (!compact) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(color)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(12.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun UserActions(
    enrollmentStatus: EnrollmentStatus,
    onEnroll: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        IconButton(
            onClick = onEnroll,
            enabled = enrollmentStatus != EnrollmentStatus.ENROLLED,
            modifier = Modifier.weight(weight = .5f).border(1.dp, Color.White, RoundedCornerShape(15.dp)),
        ) {
            Icon(
                imageVector = Icons.Default.Fingerprint,
                contentDescription = "Enroll fingerprint",
                tint = if (enrollmentStatus != EnrollmentStatus.ENROLLED)
                    MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
        }

        Spacer(Modifier.padding(4.dp))

        IconButton(
            onClick = onDelete,
            modifier = Modifier.weight(weight = .5f).border(1.dp, Color.White, RoundedCornerShape(15.dp)),
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete user",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun LoadingUsers() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            strokeWidth = 4.dp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Loading users...",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun EmptyUsersState(
    searchQuery: String,
    onAddUserClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.People,
            contentDescription = "No users",
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (searchQuery.isNotEmpty()) {
                "No users found for '$searchQuery'"
            } else {
                "No users yet"
            },
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (searchQuery.isNotEmpty()) {
                "Try a different search term"
            } else {
                "Add your first user to get started"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        if (searchQuery.isEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onAddUserClick,
                shape = MaterialTheme.shapes.large
            ) {
                Icon(Icons.Default.Add, "Add")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add First User")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserDetailsDialog(
    user: User,
    onDismiss: () -> Unit,
    onEnroll: () -> Unit,
    onDelete: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            UserAvatar(user = user, size = 48.dp)
        },
        title = {
            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                UserDetailsRow(
                    icon = Icons.Default.Fingerprint,
                    label = "Enrollment Status",
                    value = user.enrollmentStatus.toString().replace('_', ' ')
                )

                UserDetailsRow(
                    icon = Icons.Default.Numbers,
                    label = "Fingerprints",
                    value = user.fingerprintCount.toString()
                )

                user.lastAccess?.let {
                    UserDetailsRow(
                        icon = Icons.Default.AccessTime,
                        label = "Last Access",
                        value = formatDate(it)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDelete) {
                Text("Delete", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            if (user.enrollmentStatus != EnrollmentStatus.ENROLLED) {
                Button(onClick = onEnroll) {
                    Text("Enroll Fingerprint")
                }
            }
        }
    )
}

@Composable
private fun UserDetailsRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

// Helper functions
private fun getAvatarColor(name: String): Color {
    val colors = listOf(
        Color(0xFFE91E63), // Pink
        Color(0xFF9C27B0), // Purple
        Color(0xFF3F51B5), // Indigo
        Color(0xFF2196F3), // Blue
        Color(0xFF00BCD4), // Cyan
        Color(0xFF4CAF50), // Green
        Color(0xFFFF9800), // Orange
        Color(0xFF795548)  // Brown
    )
    val index = name.hashCode() % colors.size
    return colors[if (index < 0) -index else index]
}

private fun formatDate(date: LocalDateTime): String {
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm")
    return date.format(formatter)
}

// Preview function
@Preview(showBackground = true)
@Composable
fun PreviewUsersListScreen() {
    MaterialTheme {
        val sampleUsers = remember {
            listOf(
                User(
                    id = "1",
                    name = "Arvind Kumar",
                    email = "arvind@bandymoot.com",
                    enrollmentStatus = EnrollmentStatus.ENROLLED,
                    lastAccess = LocalDateTime.now().minusHours(2),
                    fingerprintCount = 2
                ),
                User(
                    id = "2",
                    name = "Afnan Khan",
                    email = "afnan@bandymoot.com",
                    enrollmentStatus = EnrollmentStatus.PENDING,
                    lastAccess = LocalDateTime.now().minusDays(1),
                    fingerprintCount = 1
                ),
                User(
                    id = "3",
                    name = "Ayushi",
                    email = "ayushi@bandymoot.com",
                    enrollmentStatus = EnrollmentStatus.NOT_ENROLLED,
                    lastAccess = null,
                    fingerprintCount = 0
                ),
                User(
                    id = "4",
                    name = "Adarsh Vishwakarma",
                    email = "adarsh@bandymoot.com",
                    enrollmentStatus = EnrollmentStatus.EXPIRED,
                    lastAccess = LocalDateTime.now().minusMonths(1),
                    fingerprintCount = 1
                )
            )
        }

        UsersListScreen(
            users = sampleUsers,
            displayMode = UserListDisplayMode.LIST,
            onUserClick = { /* Handle click */ },
            onEnrollUser = { /* Handle enroll */ },
            onDeleteUser = { /* Handle delete */ },
            onSearchQueryChanged = { /* Handle search */ },
            isLoading = false,
            searchQuery = ""
        )
    }
}