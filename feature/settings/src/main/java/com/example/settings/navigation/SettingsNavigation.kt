package com.example.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.settings.SettingsScreen
import com.example.settings.ColorPickerScreen
import com.example.settings.HapticsSettingsScreen
import com.example.settings.PinCodeScreen
import com.example.settings.SyncFrequencyScreen
import com.example.settings.LanguageSwitchScreen
import com.example.settings.AppInfoScreen
import kotlinx.serialization.Serializable
import com.example.settings.PinCodeViewModel
import com.example.settings.PinCodeMode


// route to Settings screen
@Serializable
data object SettingsScreenRoute

fun NavGraphBuilder.settingsScreen(
    navigateToColorPicker: () -> Unit,
    navigateToHaptics: () -> Unit,
    navigateToPinCode: () -> Unit,
    navigateToSyncFrequency: () -> Unit,
    navigateToLanguageSwitch: () -> Unit,
    navigateToAppInfo: () -> Unit
) {
    composable<SettingsScreenRoute> {
        SettingsScreen(
            navigateToColorPicker = navigateToColorPicker,
            navigateToHaptics = navigateToHaptics,
            navigateToPinCode = navigateToPinCode,
            navigateToSyncFrequency = navigateToSyncFrequency,
            navigateToLanguageSwitch = navigateToLanguageSwitch,
            navigateToAppInfo = navigateToAppInfo
        )
    }
}

@Serializable
data object ColorPickerScreenRoute

@Serializable
data object HapticsSettingsScreenRoute

@Serializable
data object PinCodeScreenRoute

@Serializable
data object SyncFrequencyScreenRoute

@Serializable
data object LanguageSwitchScreenRoute

@Serializable
data object AppInfoScreenRoute

fun NavGraphBuilder.colorPickerScreen() {
    composable<ColorPickerScreenRoute> {
        ColorPickerScreen()
    }
}

fun NavGraphBuilder.hapticsSettingsScreen() {
    composable<HapticsSettingsScreenRoute> {
        HapticsSettingsScreen()
    }
}

fun NavGraphBuilder.pinCodeScreen() {
    composable<PinCodeScreenRoute> {
        PinCodeScreen(
            mode = PinCodeMode.Set,
            onSuccess = {},
            onSetMode = { /* no-op, т.к. всегда установка */ }
        )
    }
}

fun NavGraphBuilder.syncFrequencyScreen() {
    composable<SyncFrequencyScreenRoute> {
        SyncFrequencyScreen()
    }
}

fun NavGraphBuilder.languageSwitchScreen() {
    composable<LanguageSwitchScreenRoute> {
        LanguageSwitchScreen()
    }
}

fun NavGraphBuilder.appInfoScreen() {
    composable<AppInfoScreenRoute> {
        AppInfoScreen()
    }
}
