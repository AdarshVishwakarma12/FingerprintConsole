package com.bandymoot.fingerprint.app.ui.screen.enroll_device.component

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
fun CustomField(
    colors: AuthColors,
    inputString: String,
    placeholderString: String,
    labelString: String,
    isError: Boolean, // showEmailError
    enabled: Boolean, // !isLoading
    onTextChanged: (String) -> Unit
) {
    // Add logic for
    // isLoading + isEmailValid
    OutlinedTextField(
        value = inputString,
        onValueChange = onTextChanged,
        placeholder = { Text(placeholderString, color = Color.Black.copy(alpha = 0.3f)) },
        label = { Text(labelString) },
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
            // focused
            focusedLabelColor = Color.Black,
            // enabled => false
            disabledBorderColor = colors.border.copy(alpha = 0.5f),
            disabledTextColor = colors.primaryText.copy(alpha = 0.6f),
            disabledPlaceholderColor = colors.secondaryText.copy(alpha = 0.5f),
            disabledLabelColor = colors.secondaryText.copy(alpha = 0.5f),
            disabledContainerColor = Color.Transparent,
            // isError => true
            errorBorderColor = Color.Red,
            errorTextColor = Color.Red,
            errorCursorColor = Color.Red,
            errorLabelColor = Color.Red,
            errorPlaceholderColor = Color.Red.copy(alpha = 0.6f)
        )
    )
}
