package com.wcsm.confectionaryadmin.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        "user_preferences", Context.MODE_PRIVATE
    )

    fun saveUser(email: String, password: String) {
        preferences.edit().apply {
            putString("email", email)
            putString("password", password)
            putBoolean("is_logged_in", true)
            apply()
        }
    }

    fun clearUser() {
        preferences.edit().apply {
            remove("email")
            remove("password")
            putBoolean("is_logged_in", false)
            apply()
        }
    }

    fun getUser(): Pair<String?, String?> {
        val email = preferences.getString("email", null)
        val password = preferences.getString("password", null)
        return Pair(email, password)
    }

    fun isLoggedIn(): Boolean {
        return preferences.getBoolean("is_logged_in", false)
    }
}