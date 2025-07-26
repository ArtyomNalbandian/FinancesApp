package com.example.common.util

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private const val SYNC_PREFERENCES_NAME = "sync_preferences"
private val Context.syncDataStore by preferencesDataStore(name = SYNC_PREFERENCES_NAME)

object SyncPreferencesDataStore {
    private val SYNC_FREQUENCY_KEY = intPreferencesKey("sync_frequency_hours")
    private const val DEFAULT_FREQUENCY = 8 // часов

    fun syncFrequencyFlow(context: Context): Flow<Int> =
        context.syncDataStore.data.map { preferences ->
            preferences[SYNC_FREQUENCY_KEY] ?: DEFAULT_FREQUENCY
        }

    suspend fun setSyncFrequency(context: Context, hours: Int) {
        context.syncDataStore.edit { preferences ->
            preferences[SYNC_FREQUENCY_KEY] = hours
        }
    }

    suspend fun getSyncFrequency(context: Context): Int =
        context.syncDataStore.data.map { it[SYNC_FREQUENCY_KEY] ?: DEFAULT_FREQUENCY }.first()
} 