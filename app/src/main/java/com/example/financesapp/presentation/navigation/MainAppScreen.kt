package com.example.financesapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
internal fun MainAppScreen() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    Scaffold(
        topBar = { FinancesTopAppBar(navController = navController) },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentDestination = currentDestination
            )
        }
    ) { padding ->
        RootNavGraph(
            navController = navController,
            modifier = Modifier.padding(padding),
        )
    }
}
