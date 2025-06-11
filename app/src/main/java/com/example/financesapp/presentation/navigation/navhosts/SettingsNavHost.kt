package com.example.financesapp.presentation.navigation.navhosts

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financesapp.presentation.settings.SettingsScreen

@Composable
fun SettingsNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "settings_main") {
        composable("settings_main") {
            SettingsScreen()
        }
    }
}