package com.example.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.settings.SettingsScreen
import kotlinx.serialization.Serializable


// route to Settings screen
@Serializable
data object SettingsScreenRoute

fun NavGraphBuilder.settingsScreen() {
    composable<SettingsScreenRoute> {
        SettingsScreen()
    }
}
