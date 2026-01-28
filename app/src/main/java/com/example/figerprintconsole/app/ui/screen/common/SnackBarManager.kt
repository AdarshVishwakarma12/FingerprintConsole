package com.example.figerprintconsole.app.ui.screen.common

import com.example.figerprintconsole.app.utils.AppConstant
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object SnackBarManager {
    private val snackBarChannel = Channel<String>(Channel.BUFFERED)
    val snackBarFlow = snackBarChannel.receiveAsFlow()

    suspend fun showMessage(message: String) {
        AppConstant.debugMessage("!!!SHOWING SNACK BAR NOW!!! - Manager!!")
        snackBarChannel.send(message)
    }
}