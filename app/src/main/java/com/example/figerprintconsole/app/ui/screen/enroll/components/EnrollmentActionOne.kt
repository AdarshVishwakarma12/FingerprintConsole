package com.example.figerprintconsole.app.ui.screen.enroll.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.figerprintconsole.app.domain.model.NewEnrollUser
import com.example.figerprintconsole.app.ui.screen.enroll.state.EnrollmentState

@Composable
fun EnrollmentActionOne(
    state: EnrollmentState,
    onRetry: () -> Unit,
    onComplete: () -> Unit,
    newUser: NewEnrollUser,
    onInputChanged: (NewEnrollUser) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        OutlinedTextField(
            value = newUser.name,
            onValueChange = { onInputChanged(newUser.copy(name = it)) },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = newUser.email,
            onValueChange = { onInputChanged(newUser.copy(email = it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = newUser.employeeId,
            onValueChange = { onInputChanged(newUser.copy(employeeId = it)) },
            label = { Text("Employee ID") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = newUser.phone,
            onValueChange = { onInputChanged(newUser.copy(phone = it)) },
            label = { Text("Phone") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = newUser.department,
            onValueChange = { onInputChanged(newUser.copy(department = it)) },
            label = { Text("Department") },
            modifier = Modifier.fillMaxWidth()
        )

        // TimeZone (simple version for now)
        OutlinedTextField(
            value = newUser.timeZone,
            onValueChange = { onInputChanged(newUser.copy(timeZone = it)) },
            label = { Text("Time Zone") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true
        )

        Spacer(modifier = Modifier.weight(1f))

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
