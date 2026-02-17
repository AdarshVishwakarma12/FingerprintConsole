package com.bandymoot.fingerprint.app.domain.validation


object EmailValidator {
    private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

    fun isValid(input: String): Boolean {
        return EMAIL_REGEX.matches(input)
    }
}