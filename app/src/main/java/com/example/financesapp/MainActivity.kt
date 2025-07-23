package com.example.financesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import com.example.financesapp.presentation.MainAppScreen
import com.example.financesapp.ui.theme.FinancesAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.settings.SettingsViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: com.example.settings.SettingsViewModel = viewModel()
            val isDarkTheme by settingsViewModel.isDarkTheme.collectAsStateWithLifecycle()
            val paletteId by settingsViewModel.paletteId.collectAsStateWithLifecycle()
            val palettes = settingsViewModel.colorPalettes
            val palette = palettes.getOrNull(paletteId) ?: palettes.first()
            FinancesAppTheme(
                darkTheme = isDarkTheme,
                primaryColor = androidx.compose.ui.graphics.Color(palette.primary),
                secondaryColor = androidx.compose.ui.graphics.Color(palette.secondary),
                tertiaryColor = androidx.compose.ui.graphics.Color(palette.tertiary)
            ) {
                MainAppScreen()
            }
        }
    }
}
