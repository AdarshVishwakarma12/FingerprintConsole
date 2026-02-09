package com.bandymoot.fingerprint.app.data.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("manager_email") val managerEmail: String,
    @SerializedName("manager_password") val managerPassword: String
)