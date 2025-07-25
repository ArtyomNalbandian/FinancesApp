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

fun NavGraphBuilder.colorPickerScreen(
    navigateBack: () -> Unit
) {
    composable<ColorPickerScreenRoute> {
        ColorPickerScreen(navigateBack = navigateBack)
    }
}

fun NavGraphBuilder.hapticsSettingsScreen(
    navigateBack: () -> Unit
) {
    composable<HapticsSettingsScreenRoute> {
        HapticsSettingsScreen(navigateBack = navigateBack)
    }
}

fun NavGraphBuilder.pinCodeScreen(
    navigateBack: () -> Unit
) {
    composable<PinCodeScreenRoute> {
        PinCodeScreen(
            mode = PinCodeMode.Set,
            onSuccess = {},
            onSetMode = { /* no-op, т.к. всегда установка */ },
            navigateBack = navigateBack
        )
    }
}

fun NavGraphBuilder.syncFrequencyScreen(
    navigateBack: () -> Unit
) {
    composable<SyncFrequencyScreenRoute> {
        SyncFrequencyScreen(navigateBack = navigateBack)
    }
}

fun NavGraphBuilder.languageSwitchScreen(
    navigateBack: () -> Unit
) {
    composable<LanguageSwitchScreenRoute> {
        LanguageSwitchScreen(navigateBack = navigateBack)
    }
}

fun NavGraphBuilder.appInfoScreen(
    navigateBack: () -> Unit
) {
    composable<AppInfoScreenRoute> {
        AppInfoScreen(navigateBack = navigateBack)
    }
}
