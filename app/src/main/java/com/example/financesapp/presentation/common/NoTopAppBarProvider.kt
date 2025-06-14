package com.example.financesapp.presentation.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

object NoTopAppBarProvider : TopAppBarProvider {
    @Composable
    override fun ProvideTopAppBar(navController: NavHostController) {}
}