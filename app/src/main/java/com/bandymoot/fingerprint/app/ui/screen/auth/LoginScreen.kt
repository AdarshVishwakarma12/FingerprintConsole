package com.bandymoot.fingerprint.app.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bandymoot.fingerprint.app.domain.model.AppError
import com.bandymoot.fingerprint.app.ui.screen.auth.event.LoginUiEvent
import com.bandymoot.fingerprint.app.utils.AppThemeMode
import com.bandymoot.fingerprint.app.utils.authColors
import com.bandymoot.fingerprint.app.ui.screen.auth.components.AppTitle
import com.bandymoot.fingerprint.app.ui.screen.auth.components.AuthErrorBanner
import com.bandymoot.fingerprint.app.ui.screen.auth.components.EmailInputField
import com.bandymoot.fingerprint.app.ui.screen.auth.components.LoginHeader
import com.bandymoot.fingerprint.app.ui.screen.auth.components.OrDivider
import com.bandymoot.fingerprint.app.ui.screen.auth.components.PasswordInputField
import com.bandymoot.fingerprint.app.ui.screen.auth.components.PrimaryActionButton
import com.bandymoot.fingerprint.app.ui.screen.auth.components.SignUpRedirect
import com.bandymoot.fingerprint.app.ui.screen.auth.components.SocialAuthSection
import com.bandymoot.fingerprint.app.ui.screen.auth.components.TermsAndPrivacy

@Composable
fun LoginScreen(
    currentTheme: AppThemeMode,
    onSignUpClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    // There is no current use of it!
    // Must need to update! As soon as possible!
    val errorMessage = when (val error = uiState.authError) {
        is AppError.Unauthorized -> "Invalid email or password"
        is AppError.Network -> "No internet connection"
        is AppError.Server -> "Server error. Try again later"
        is AppError.RateLimited -> "Too many attempts. Slow down."
        else -> error?.message ?: "Something went wrong"
    }

    val colors = authColors(currentTheme)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = 24.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
                viewModel.observeUiEvent(LoginUiEvent.CheckEmailValidity)
                viewModel.observeUiEvent(LoginUiEvent.CheckPasswordValidity)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.height(80.dp))

        AppTitle(text = "Fingerprint Console", colors = colors)

        Spacer(modifier = Modifier.height(48.dp))

        LoginHeader(colors)

        Spacer(modifier = Modifier.height(24.dp))

        EmailInputField(
            colors = colors,
            email = uiState.email,
            isError = uiState.showEmailError,
            enabled = !uiState.isLoading,
            onEmailChanged = { value ->
                viewModel.observeUiEvent(LoginUiEvent.EmailChanged(value))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordInputField(
            colors = colors,
            password = uiState.password,
            isError = (uiState.showPasswordError && !uiState.isPasswordValid),
            enabled = !uiState.isLoading,
            onPasswordChanged = { value ->
                viewModel.observeUiEvent(LoginUiEvent.PasswordChanged(value))
            }
        )

        if (uiState.authError != null) {

            Spacer(modifier = Modifier.height(22.dp))

            AuthErrorBanner(
                message = errorMessage,
                colors = colors,
                onClearClick = {
                    viewModel.observeUiEvent(LoginUiEvent.ClearError)
                },
                modifier = Modifier.padding(vertical = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        PrimaryActionButton(
            text = "Login",
            colors = colors,
            isValidAll = ( uiState.isEmailValid && uiState.isPasswordValid ),
            isLoading = uiState.isLoading,
            onClick = {
                viewModel.observeUiEvent(LoginUiEvent.LoginClicked)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        OrDivider(colors)

        Spacer(modifier = Modifier.height(24.dp))

        SocialAuthSection(colors)

        Spacer(modifier = Modifier.height(24.dp))

        SignUpRedirect(
            colors = colors,
            onSignUpClick = onSignUpClick
        )

        Spacer(modifier = Modifier.weight(1f))

        TermsAndPrivacy(colors)
    }
}