package com.example.financesapp.presentation.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

interface TopAppBarProvider {
    @Composable
    fun ProvideTopAppBar(navController: NavHostController)
}

interface FloatingActionButtonProvider {
    @Composable
    fun ProvideFloatingActionButton(navController: NavHostController)
}

interface ScreenComponents {
    val topAppBarProvider: TopAppBarProvider?
    val floatingActionButtonProvider: FloatingActionButtonProvider?
}