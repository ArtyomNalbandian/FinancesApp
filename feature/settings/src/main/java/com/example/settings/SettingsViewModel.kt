package com.example.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.util.ThemePreferencesDataStore
import com.example.common.util.ColorPreferencesDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    val isDarkTheme: StateFlow<Boolean> =
        ThemePreferencesDataStore.isDarkThemeFlow(context)
            .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val paletteId: StateFlow<Int> =
        ColorPreferencesDataStore.paletteIdFlow(context)
            .stateIn(viewModelScope, SharingStarted.Lazily, 0)

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

    // Цветовые палитры // TODO: убрать это убожество и выбрать нормальные цвета
    val colorPalettes = listOf(
        ColorPalette(
            primary = 0xFF4CAF50.toInt(), // Green
            secondary = 0xFF8BC34A.toInt(),
            tertiary = 0xFF7C4DFF.toInt(),
            name = "Зелёная"
        ),
        ColorPalette(
            primary = 0xFF2196F3.toInt(), // Blue
            secondary = 0xFF03A9F4.toInt(),
            tertiary = 0xFF00BCD4.toInt(),
            name = "Синяя"
        ),
        ColorPalette(
            primary = 0xFFFF9800.toInt(), // Orange
            secondary = 0xFFFFC107.toInt(),
            tertiary = 0xFFFF5722.toInt(),
            name = "Оранжевая"
        )
    )
}

data class ColorPalette(val primary: Int, val secondary: Int, val tertiary: Int, val name: String)
