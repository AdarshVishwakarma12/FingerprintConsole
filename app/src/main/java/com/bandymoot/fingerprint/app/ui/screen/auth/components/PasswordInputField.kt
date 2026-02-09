package com.bandymoot.fingerprint.app.ui.screen.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.app.utils.AuthColors

@Composable
fun PasswordInputField(
    colors: AuthColors,
    password: String,
    isError: Boolean,          // showPasswordError
    enabled: Boolean,          // !isLoading
    onPasswordChanged: (String) -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChanged,
        placeholder = { Text("Password") },
        singleLine = true,
        enabled = enabled,
        isError = isError,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        visualTransformation = if (passwordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible)
                        Icons.Default.VisibilityOff
                    else
                        Icons.Default.Visibility,
                    contentDescription = null,
                    tint = colors.primaryText
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colors.primaryText,
            unfocusedBorderColor = colors.border,
            focusedTextColor = colors.primaryText,
            unfocusedTextColor = colors.primaryText,
            cursorColor = colors.primaryText,

            // Disabled
            disabledBorderColor = colors.border.copy(alpha = 0.5f),
            disabledTextColor = colors.primaryText.copy(alpha = 0.6f),
            disabledPlaceholderColor = colors.secondaryText.copy(alpha = 0.5f),
            disabledContainerColor = Color.Transparent,

            // Error
            errorBorderColor = Color.Red,
            errorTextColor = Color.Red,
            errorCursorColor = Color.Red,
            errorPlaceholderColor = Color.Red.copy(alpha = 0.6f)
        )
    )
}
