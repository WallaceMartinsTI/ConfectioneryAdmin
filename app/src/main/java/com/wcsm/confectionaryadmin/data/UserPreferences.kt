package com.wcsm.confectionaryadmin.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val masterKeyAlias = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val preferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "user_preferences",
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun getSyncUpConfirmDialogPreference(userId: String): Boolean? {
        val key = "${userId}_sync_up_confirm".lowercase()
        return if(preferences.contains(key)) {
            preferences.getBoolean(key, true)
        } else {
            null
        }
    }

    fun saveSyncUpConfirmDialogPreference(userId: String, status: Boolean) {
        val key = "${userId}_sync_up_confirm".lowercase()
        preferences.edit().apply {
            putBoolean(key, status)
            apply()
        }
        getSyncUpConfirmDialogPreference(userId)
    }

    fun getSyncDownConfirmDialogPreference(userId: String): Boolean? {
        val key = "${userId}_sync_down_confirm".lowercase()
        return if(preferences.contains(key)) {
            preferences.getBoolean(key, true)
        } else {
            null
        }
    }

    fun saveSyncDownConfirmDialogPreference(userId: String, status: Boolean) {
        val key = "${userId}_sync_down_confirm".lowercase()
        preferences.edit().apply {
            putBoolean(key, status)
            apply()
        }
        getSyncDownConfirmDialogPreference(userId)
    }

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