package com.bandymoot.fingerprint.app.ui.screen.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.utils.AuthColors

@Composable
fun EmailInputField(
    colors: AuthColors,
    email: String,
    isError: Boolean, // showEmailError
    enabled: Boolean, // !isLoading
    onEmailChanged: (String) -> Unit
) {
    // Add logic for
    // isLoading + isEmailValid
    OutlinedTextField(
        value = email,
        onValueChange = onEmailChanged,
        placeholder = { Text("email@domain.com") },
        singleLine = true,
        enabled = enabled,
        isError = isError,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colors.primaryText,
            unfocusedBorderColor = colors.border,
            focusedTextColor = colors.primaryText,
            unfocusedTextColor = colors.primaryText,
            cursorColor = colors.primaryText,
            // enabled => false
            disabledBorderColor = colors.border.copy(alpha = 0.5f),
            disabledTextColor = colors.primaryText.copy(alpha = 0.6f),
            disabledPlaceholderColor = colors.secondaryText.copy(alpha = 0.5f),
            disabledContainerColor = Color.Transparent,
            // isError => true
            errorBorderColor = Color.Red,
            errorTextColor = Color.Red,
            errorCursorColor = Color.Red,
            errorPlaceholderColor = Color.Red.copy(alpha = 0.6f)
        )
    )
}
