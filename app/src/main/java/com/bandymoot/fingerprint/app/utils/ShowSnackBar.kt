package com.bandymoot.fingerprint.app.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bandymoot.fingerprint.app.ui.screen.common.SnackBarManager
import kotlinx.coroutines.launch

fun ViewModel.showSnackBar(message: String) {
    viewModelScope.launch {
        AppConstant.debugMessage("!!!SHOWING SNACK BAR NOW!!! - Mapper!")
        SnackBarManager.showMessage(message)
    }
}