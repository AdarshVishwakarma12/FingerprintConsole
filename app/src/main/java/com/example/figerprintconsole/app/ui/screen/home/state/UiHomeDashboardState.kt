package com.example.figerprintconsole.app.ui.screen.home.state

import com.example.figerprintconsole.app.domain.model.Device

data class UiHomeDashboardState(
    val isLoading: Boolean = true,
    val deviceList: List<Device> = emptyList(),
    val isErrorState: Boolean = false,
    val errorType: UiHomeDashboardErrorState = UiHomeDashboardErrorState.NoError
)

sealed class UiHomeDashboardErrorState() {
    object NoError: UiHomeDashboardErrorState()
    class Unknown(val message: String): UiHomeDashboardErrorState()
}