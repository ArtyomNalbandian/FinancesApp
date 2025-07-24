package com.example.common.util

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object PinCodeStorage {
    private const val PREFS_NAME = "secure_pin_prefs"
    private const val KEY_PIN = "pin_code"

    private fun getPrefs(context: Context) = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    suspend fun savePin(context: Context, pin: String) = withContext(Dispatchers.IO) {
        getPrefs(context).edit().putString(KEY_PIN, pin).apply()
    }

    suspend fun getPin(context: Context): String? = withContext(Dispatchers.IO) {
        getPrefs(context).getString(KEY_PIN, null)
    }

    suspend fun isPinSet(context: Context): Boolean = withContext(Dispatchers.IO) {
        getPrefs(context).contains(KEY_PIN)
    }

    suspend fun clearPin(context: Context) = withContext(Dispatchers.IO) {
        getPrefs(context).edit().remove(KEY_PIN).apply()
    }
}
