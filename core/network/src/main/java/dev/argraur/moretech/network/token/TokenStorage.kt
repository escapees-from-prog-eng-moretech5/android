package dev.argraur.moretech.network.token

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenStorage @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    // Save the token in SharedPreferences
    fun setToken(token: String) {
        with(sharedPreferences.edit()) {
            putString(TOKEN_KEY, token)
            apply()
        }
    }

    // Retrieve the token from SharedPreferences
    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    companion object {
        private const val PREFERENCES_NAME = "auth_prefs"
        private const val TOKEN_KEY = "auth_token"
    }
}