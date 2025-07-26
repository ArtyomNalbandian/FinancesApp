package com.example.common.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val LOCALE_PREFERENCES_NAME = "locale_preferences"
private val Context.localeDataStore by preferencesDataStore(name = LOCALE_PREFERENCES_NAME)

object LocalePreferencesDataStore {
    private val LOCALE_KEY = stringPreferencesKey("locale")
    private const val DEFAULT_LOCALE = "ru"

    fun localeFlow(context: Context): Flow<String> =
        context.localeDataStore.data.map { preferences ->
            preferences[LOCALE_KEY] ?: DEFAULT_LOCALE
        }

    suspend fun setLocale(context: Context, locale: String) {
        context.localeDataStore.edit { preferences ->
            preferences[LOCALE_KEY] = locale
        }
    }

    suspend fun getLocale(context: Context): String =
        context.localeDataStore.data.map { it[LOCALE_KEY] ?: DEFAULT_LOCALE }.first()
}
