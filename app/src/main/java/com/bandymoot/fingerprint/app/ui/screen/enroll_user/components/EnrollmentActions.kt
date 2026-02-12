package com.bandymoot.fingerprint.app.ui.screen.enroll_user.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentScreenState
import com.bandymoot.fingerprint.app.ui.screen.enroll_user.state.EnrollmentState
import com.bandymoot.fingerprint.app.utils.AppConstant

@Composable
fun EnrollmentActions(
    uiState: EnrollmentState,
    onStartEnrollment: () -> Unit,
    onRetry: () -> Unit,
    onComplete: () -> Unit,
    onCompleteEnrollment: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize() // take full screen
    ) {
        // Status Section (takes remaining space)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(uiState.enrollmentScreenState) {
                // Add the current UI State -> Success / Failed
                is EnrollmentScreenState.Enrolled -> {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Enrollment Completed",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                is EnrollmentScreenState.IDLE -> {
                    AppConstant.debugMessage("The State is IDLE @ Enrollment Actions...")
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = "Start Enrollment",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Ready to Start Enrollment",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                else -> { }
            }
        }

        // Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when {
                uiState.errorMessage != null -> {
                    OutlinedButton(
                        onClick = onRetry,
                        modifier = Modifier.weight(1f),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text("Retry")
                    }
                }

                uiState.enrollmentScreenState == EnrollmentScreenState.IDLE -> {
                    Button(
                        onClick = onStartEnrollment,
                        modifier = Modifier.weight(1f),
                        shape = MaterialTheme.shapes.large,
                        enabled = !uiState.isLoading
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(
                                text = "Start Enrollment",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                uiState.enrollmentScreenState == EnrollmentScreenState.Enrolled -> {
                    Button(
                        onClick = onCompleteEnrollment,
                        modifier = Modifier.weight(1f),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text(
                            text = "Continue",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
