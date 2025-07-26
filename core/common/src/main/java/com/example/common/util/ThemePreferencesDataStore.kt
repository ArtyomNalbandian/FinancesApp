package com.example.common.util

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val THEME_PREFERENCES_NAME = "theme_preferences"
private val Context.dataStore by preferencesDataStore(name = THEME_PREFERENCES_NAME)

object ThemePreferencesDataStore {
    private val IS_DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")

    fun isDarkThemeFlow(context: Context): Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[IS_DARK_THEME_KEY] ?: false
        }

    suspend fun setDarkTheme(context: Context, isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_THEME_KEY] = isDark
        }
    }
}
