package com.bandymoot.fingerprint.app.ui.screen.enroll.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bandymoot.fingerprint.app.domain.model.NewEnrollUser
import com.bandymoot.fingerprint.app.ui.screen.enroll.state.EnrollmentScreenState
import com.bandymoot.fingerprint.app.ui.screen.enroll.state.EnrollmentState

@Composable
fun EnrollmentActionOne(
    uiState: EnrollmentScreenState,
    state: EnrollmentState,
    onRetry: () -> Unit,
    onComplete: () -> Unit,
    newUser: NewEnrollUser,
    onInputChanged: (NewEnrollUser) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.verticalScroll(rememberScrollState()),
    ) {

        // These data need to be validated && Must be stored in viewModel temporarly..!!
        // Otherwise there will be some side-effects!
        OutlinedTextField(
            value = newUser.name,
            onValueChange = { onInputChanged(newUser.copy(name = it)) },
            placeholder = { Text("Name") },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = newUser.email,
            onValueChange = { onInputChanged(newUser.copy(email = it)) },
            placeholder = { Text("Email") },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = newUser.employeeId,
            onValueChange = { onInputChanged(newUser.copy(employeeId = it)) },
            placeholder = { Text("Employee ID") },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = newUser.phone,
            onValueChange = { onInputChanged(newUser.copy(phone = it)) },
            placeholder = { Text("Phone") },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = newUser.department,
            onValueChange = { onInputChanged(newUser.copy(department = it)) },
            placeholder = { Text("Department") },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )

        // TimeZone (simple version for now)
        OutlinedTextField(
            value = newUser.timeZone,
            onValueChange = { onInputChanged(newUser.copy(timeZone = it)) },
            placeholder = { Text("Time Zone") },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onComplete,
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.large,
                enabled = true
            ) {
                Text(
                    text = "Continue",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (state.errorMessage != null) {
                OutlinedButton(
                    onClick = onRetry,
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text("Retry")
                }
            }
        }
    }
}