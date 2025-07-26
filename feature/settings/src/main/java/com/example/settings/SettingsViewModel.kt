package com.example.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.util.ColorPreferencesDataStore
import com.example.common.util.HapticsPreferencesDataStore
import com.example.common.util.LocalePreferencesDataStore
import com.example.common.util.SyncPreferencesDataStore
import com.example.common.util.ThemePreferencesDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
            .stateIn(viewModelScope, SharingStarted.Eagerly, "ru")

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
        }
    }

    val colorPalettes = listOf(
        ColorPalette(
            lightPrimary = 0xFF2AE881.toInt(),
            lightSecondary = 0xFFD4FAE6.toInt(),
            lightTertiary = 0xFFFEF7FF.toInt(),
            darkPrimary = 0xFF1B5E20.toInt(),
            darkSecondary = 0xFF2E7D32.toInt(),
            darkTertiary = 0xFF000000.toInt(),
            nameRes = R.string.palette_green
        ),
        ColorPalette(
            lightPrimary = 0xFF2196F3.toInt(),
            lightSecondary = 0xFFBBDEFB.toInt(),
            lightTertiary = 0xFFFEF7FF.toInt(),
            darkPrimary = 0xFF0D47A1.toInt(),
            darkSecondary = 0xFF1565C0.toInt(),
            darkTertiary = 0xFF000000.toInt(),
            nameRes = R.string.palette_blue
        ),
        ColorPalette(
            lightPrimary = 0xFFFF9800.toInt(),
            lightSecondary = 0xFFFFE0B2.toInt(),
            lightTertiary = 0xFFFEF7FF.toInt(),
            darkPrimary = 0xFFE65100.toInt(),
            darkSecondary = 0xFFF57C00.toInt(),
            darkTertiary = 0xFF000000.toInt(),
            nameRes = R.string.palette_orange
        )
    )

    val hapticsEffects = listOf(
        R.string.haptic_short,
        R.string.haptic_long,
        R.string.haptic_double,
    )
}

data class ColorPalette(
    val lightPrimary: Int,
    val lightSecondary: Int,
    val lightTertiary: Int,
    val darkPrimary: Int,
    val darkSecondary: Int,
    val darkTertiary: Int,
    val nameRes: Int
) {
    fun getPrimary(isDarkTheme: Boolean): Int = if (isDarkTheme) darkPrimary else lightPrimary
    fun getSecondary(isDarkTheme: Boolean): Int = if (isDarkTheme) darkSecondary else lightSecondary
    fun getTertiary(isDarkTheme: Boolean): Int = if (isDarkTheme) darkTertiary else lightTertiary
}
