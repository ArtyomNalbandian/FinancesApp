package com.example.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.util.ThemePreferencesDataStore
import com.example.common.util.ColorPreferencesDataStore
import com.example.common.util.HapticsPreferencesDataStore
import com.example.common.util.SyncPreferencesDataStore
import com.example.common.util.LocalePreferencesDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import android.content.Context

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    val isDarkTheme: StateFlow<Boolean> =
        ThemePreferencesDataStore.isDarkThemeFlow(context)
            .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val paletteId: StateFlow<Int> =
        ColorPreferencesDataStore.paletteIdFlow(context)
            .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    val hapticsEnabled: StateFlow<Boolean> =
        HapticsPreferencesDataStore.hapticsEnabledFlow(context)
            .stateIn(viewModelScope, SharingStarted.Lazily, true)

    val hapticsEffect: StateFlow<Int> =
        HapticsPreferencesDataStore.hapticsEffectFlow(context)
            .stateIn(viewModelScope, SharingStarted.Lazily, 0)

    val syncFrequency: StateFlow<Int> =
        SyncPreferencesDataStore.syncFrequencyFlow(context)
            .stateIn(viewModelScope, SharingStarted.Lazily, 8)

    val locale: StateFlow<String> =
        LocalePreferencesDataStore.localeFlow(context)
            .stateIn(viewModelScope, SharingStarted.Lazily, "ru")

    fun setDarkTheme(isDark: Boolean) {
        viewModelScope.launch {
            ThemePreferencesDataStore.setDarkTheme(context, isDark)
        }
    }

    fun setPaletteId(paletteId: Int) {
        viewModelScope.launch {
            ColorPreferencesDataStore.setPaletteId(context, paletteId)
        }
    }

    fun setHapticsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            HapticsPreferencesDataStore.setHapticsEnabled(context, enabled)
        }
    }

    fun setHapticsEffect(effect: Int) {
        viewModelScope.launch {
            HapticsPreferencesDataStore.setHapticsEffect(context, effect)
        }
    }

    fun setSyncFrequency(hours: Int) {
        viewModelScope.launch {
            SyncPreferencesDataStore.setSyncFrequency(context, hours)
        }
    }

    fun setLocale(locale: String) {
        viewModelScope.launch {
            LocalePreferencesDataStore.setLocale(context, locale)
            context.getSharedPreferences("locale_prefs", Context.MODE_PRIVATE)
                .edit().putString("locale", locale).apply()
        }
    }

    // Цветовые палитры // TODO: убрать это убожество и выбрать нормальные цвета
    val colorPalettes = listOf(
        ColorPalette(
            primary = 0xFF4CAF50.toInt(), // Green
            secondary = 0xFF8BC34A.toInt(),
            tertiary = 0xFF7C4DFF.toInt(),
            nameRes = R.string.palette_green
        ),
        ColorPalette(
            primary = 0xFF2196F3.toInt(), // Blue
            secondary = 0xFF03A9F4.toInt(),
            tertiary = 0xFF00BCD4.toInt(),
            nameRes = R.string.palette_blue
        ),
        ColorPalette(
            primary = 0xFFFF9800.toInt(), // Orange
            secondary = 0xFFFFC107.toInt(),
            tertiary = 0xFFFF5722.toInt(),
            nameRes = R.string.palette_orange
        )
    )

    val hapticsEffects = listOf(
        R.string.haptic_short,
        R.string.haptic_long,
        R.string.haptic_double,
    )
}

data class ColorPalette(val primary: Int, val secondary: Int, val tertiary: Int, val nameRes: Int)
