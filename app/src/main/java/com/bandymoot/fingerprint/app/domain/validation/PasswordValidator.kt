package com.bandymoot.fingerprint.app.domain.validation

object PasswordValidator {
    private val PASSWORD_REGEX = "^.{4,}$".toRegex()

    fun isValid(input: String): Boolean {
        return PASSWORD_REGEX.matches(input)
    }

}