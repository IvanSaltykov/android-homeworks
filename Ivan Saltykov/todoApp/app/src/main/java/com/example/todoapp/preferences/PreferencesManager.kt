package com.example.todoapp.preferences

import android.content.Context

class PreferencesManager(context: Context) {
    companion object {
        private const val PREFERENCES_NAME = "Token"
        private const val TOKEN_KEY = "token"
        private const val EMAIL_KEY = "email"
    }
    private val preferencesSingIn = context
        .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    var email: String
        get() = preferencesSingIn.getString(EMAIL_KEY, null) ?: ""
        set(value) = preferencesSingIn
            .edit()
            .putString(EMAIL_KEY, value)
            .apply()

    var token: String
        get() = preferencesSingIn.getString(TOKEN_KEY, null) ?: ""
        set(value) = preferencesSingIn
            .edit()
            .putString(TOKEN_KEY, value)
            .apply()

    fun clearPreferences() {
        preferencesSingIn
            .edit()
            .clear()
            .apply()
    }
}
