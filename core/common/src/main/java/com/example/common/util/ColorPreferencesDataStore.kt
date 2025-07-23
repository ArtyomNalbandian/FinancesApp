package com.example.common.util

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val COLOR_PREFERENCES_NAME = "color_preferences"
private val Context.colorDataStore by preferencesDataStore(name = COLOR_PREFERENCES_NAME)

object ColorPreferencesDataStore {
    private val PALETTE_ID_KEY = intPreferencesKey("palette_id")
    private const val DEFAULT_PALETTE_ID = 0

    fun paletteIdFlow(context: Context): Flow<Int> =
        context.colorDataStore.data.map { preferences ->
            preferences[PALETTE_ID_KEY] ?: DEFAULT_PALETTE_ID
        }

    suspend fun setPaletteId(context: Context, paletteId: Int) {
        context.colorDataStore.edit { preferences ->
            preferences[PALETTE_ID_KEY] = paletteId
        }
    }
} 