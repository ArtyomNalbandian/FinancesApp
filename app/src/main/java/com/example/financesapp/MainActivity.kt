package com.example.financesapp

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.presentation.MainAppScreen
import com.example.financesapp.ui.theme.FinancesAppTheme
import com.example.settings.PinCodeMode
import com.example.settings.PinCodeScreen
import com.example.settings.PinCodeViewModel
import java.util.Locale

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
            val pinViewModel: PinCodeViewModel = viewModel()
            var pinChecked by rememberSaveable { mutableStateOf(false) }
            var pinRequired by rememberSaveable { mutableStateOf(false) }
            var splashShown by rememberSaveable { mutableStateOf(false) }
            
            LaunchedEffect(Unit) {
                pinViewModel.isPinSet {
                    pinRequired = it
                    pinChecked = !it
                }
            }
            
            FinancesAppTheme(
                darkTheme = isDarkTheme,
                primaryColor = androidx.compose.ui.graphics.Color(palette.getPrimary(isDarkTheme)),
                secondaryColor = androidx.compose.ui.graphics.Color(palette.getSecondary(isDarkTheme)),
                tertiaryColor = androidx.compose.ui.graphics.Color(palette.getTertiary(isDarkTheme))
            ) {
                if (!splashShown) {
                    SplashScreen(
                        onSplashComplete = { splashShown = true }
                    )
                } else if (pinRequired && !pinChecked) {
                    PinCodeScreen(
                        mode = PinCodeMode.Check,
                        onSuccess = { pinChecked = true },
                        onSetMode = { pinViewModel.setMode(it) }
                    )
                } else {
                    MainAppScreen()
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("locale_prefs", Context.MODE_PRIVATE)
        val locale = prefs.getString("locale", "ru") ?: "ru"
        val config = Configuration(newBase.resources.configuration)
        config.setLocale(Locale(locale))
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }
}
