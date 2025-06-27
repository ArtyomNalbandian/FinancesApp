package com.example.financesapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financesapp.presentation.common.TopBar

@Composable
fun MainAppScreen(viewModelFactory: ViewModelProvider.Factory) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    val topAppBarState = remember(currentBackStack) {
        provideTopAppBarState(currentBackStack, navController)
    }

    Scaffold(
        topBar = { TopBar(state = topAppBarState) },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentDestination = currentDestination
            )
        }
    ) { padding ->
        RootNavGraph(
            navController = navController,
            viewModelFactory = viewModelFactory,
            modifier = Modifier.padding(padding),
        )
    }
}
