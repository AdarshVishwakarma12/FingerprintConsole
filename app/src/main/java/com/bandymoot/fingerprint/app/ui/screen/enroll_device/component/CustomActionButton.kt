package com.bandymoot.fingerprint.app.ui.screen.enroll_device.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.bandymoot.fingerprint.R
import com.bandymoot.fingerprint.app.utils.AuthColors

@Composable
fun CustomActionButton(
    text: String,
    colors: AuthColors,
    onClick: () -> Unit,
    isValidAll: Boolean,
    isLoading: Boolean
) {
    val isEnabled = isValidAll && !isLoading

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.strat_blue),
            contentColor = colors.buttonText,
            disabledContainerColor = colors.buttonBackground.copy(alpha = 0.4f),
            disabledContentColor = colors.buttonText.copy(alpha = 0.6f)
        ),
        enabled = isEnabled
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.5.dp,
                color = colors.buttonText
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}