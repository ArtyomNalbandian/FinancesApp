package com.example.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
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

    fun setDarkTheme(isDark: Boolean) {
        viewModelScope.launch {
            ThemePreferencesDataStore.setDarkTheme(context, isDark)
        }
    }
}
