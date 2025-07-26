package com.example.common.util

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val HAPTICS_PREFERENCES_NAME = "haptics_preferences"
private val Context.hapticsDataStore by preferencesDataStore(name = HAPTICS_PREFERENCES_NAME)

object HapticsPreferencesDataStore {
    private val HAPTICS_ENABLED_KEY = booleanPreferencesKey("haptics_enabled")
    private val HAPTICS_EFFECT_KEY = intPreferencesKey("haptics_effect")
    private const val DEFAULT_HAPTICS_ENABLED = true
    private const val DEFAULT_HAPTICS_EFFECT = 0 // 0 - короткая, 1 - длинная, 2 - двойная

    fun hapticsEnabledFlow(context: Context): Flow<Boolean> =
        context.hapticsDataStore.data.map { preferences ->
            preferences[HAPTICS_ENABLED_KEY] ?: DEFAULT_HAPTICS_ENABLED
        }

    fun hapticsEffectFlow(context: Context): Flow<Int> =
        context.hapticsDataStore.data.map { preferences ->
            preferences[HAPTICS_EFFECT_KEY] ?: DEFAULT_HAPTICS_EFFECT
        }

    suspend fun setHapticsEnabled(context: Context, enabled: Boolean) {
        context.hapticsDataStore.edit { preferences ->
            preferences[HAPTICS_ENABLED_KEY] = enabled
        }
    }

    suspend fun setHapticsEffect(context: Context, effect: Int) {
        context.hapticsDataStore.edit { preferences ->
            preferences[HAPTICS_EFFECT_KEY] = effect
        }
    }
} 