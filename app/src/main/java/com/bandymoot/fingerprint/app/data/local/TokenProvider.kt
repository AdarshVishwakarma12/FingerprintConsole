package com.bandymoot.fingerprint.app.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.bandymoot.fingerprint.app.utils.AppConstant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TokenProvider @Inject constructor(
    private val sharedPreference: SharedPreferences
) {

    private val _tokenFlow =
        MutableStateFlow(sharedPreference.getString(AppConstant.SHARED_PREF_ACCESS_TOKEN, null))
    val tokenFLow: StateFlow<String?> = _tokenFlow.asStateFlow()

    fun getAccessToken(): String? {
        return sharedPreference.getString(AppConstant.SHARED_PREF_ACCESS_TOKEN, null)
    }

    fun getRefreshToken(): String? {
        return  sharedPreference.getString(AppConstant.SHARED_PREF_REFRESH_TOKEN, null)
    }

    fun getUserEmail(): String? {
        return sharedPreference.getString(AppConstant.SHARED_PREF_USER_EMAIL, null)
    }

    fun getUserId(): String? {
        return sharedPreference.getString(AppConstant.SHARED_PREF_USER_ID, null)
    }

    fun saveAccessToken(token: String) {

        sharedPreference.edit {
            putString(AppConstant.SHARED_PREF_ACCESS_TOKEN, token)
        }

        // update the token flow value
        // it will trigger the flow function in root composable!
        _tokenFlow.value = token
    }

    fun saveRefreshToken(token: String) {
        sharedPreference.edit {
            putString(AppConstant.SHARED_PREF_REFRESH_TOKEN, token)
        }
    }

    fun saveUserEmail(email: String) {
        sharedPreference.edit {
            putString(AppConstant.SHARED_PREF_USER_EMAIL, email)
        }
    }

    fun saveUserId(id: String) {
        sharedPreference.edit {
            putString(AppConstant.SHARED_PREF_USER_ID, id)
        }
    }

    fun saveLoginTimestamp() {
        sharedPreference.edit {
            putLong(AppConstant.SHARED_PREF_LOGIN_TIMESTAMP, System.currentTimeMillis())
        }
    }

    fun removeAllToken() {
        println("Token Removed!!")
        sharedPreference.edit {
            clear()
        }
        _tokenFlow.value = null
    }
}