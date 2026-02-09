package com.bandymoot.fingerprint.app.utils

fun String.isValidPassword(): Boolean {
    val length = this.length
    return (length >= AppConstant.MIN_PASSWORD_LENGTH && length <= AppConstant.MAX_PASSWORD_LENGTH)
}