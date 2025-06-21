package com.example.financesapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financesapp.R
import com.example.financesapp.presentation.common.TopBar
import com.example.financesapp.utils.NetworkMonitor


@Composable
fun MainAppScreen() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    val topAppBarState = remember(currentBackStack) {
        provideTopAppBarState(currentBackStack, navController)
    }

    Scaffold(
        topBar = { TopBar(state = topAppBarState) },
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    Triple(Screen.ExpensesGraph, R.drawable.expenses, "Расходы"),
                    Triple(Screen.IncomeGraph, R.drawable.income, "Доходы"),
                    Triple(Screen.AccountGraph, R.drawable.account, "Счет"),
                    Triple(Screen.ArticlesGraph, R.drawable.articles_new, "Статьи"),
                    Triple(Screen.SettingsGraph, R.drawable.settings, "Настройки")
                )

                items.forEach { (screen, icon, label) ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(icon),
                                contentDescription = label,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text(label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }
        },
    ) { padding ->
        RootNavGraph(
            navController = navController,
            modifier = Modifier.padding(padding),
        )
    }
}