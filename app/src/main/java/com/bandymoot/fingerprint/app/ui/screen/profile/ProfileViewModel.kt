package com.bandymoot.fingerprint.app.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.data.local.TokenProvider
import com.bandymoot.fingerprint.app.domain.repository.AuthRepository
import com.bandymoot.fingerprint.app.ui.screen.profile.event.ProfileScreenUiEvent
import com.bandymoot.fingerprint.app.ui.screen.profile.state.ProfileScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ProfileScreenUiState())
    val uiState: StateFlow<ProfileScreenUiState> get() = _uiState

    fun onEvent(event: ProfileScreenUiEvent) {
        when(event) {
            is ProfileScreenUiEvent.LOGOUT -> {
                viewModelScope.launch(Dispatchers.IO) { authRepository.logout() }
            }
        }
    }
}