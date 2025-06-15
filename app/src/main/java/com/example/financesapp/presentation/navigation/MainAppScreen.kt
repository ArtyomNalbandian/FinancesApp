package com.example.financesapp.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financesapp.R
import com.example.financesapp.presentation.account.AccountScreenComponents
import com.example.financesapp.presentation.articles.ArticlesScreenComponents
import com.example.financesapp.presentation.expenses.ExpensesScreenComponents
import com.example.financesapp.presentation.income.IncomeScreenComponents
import com.example.financesapp.presentation.navigation.routes.Screen
import com.example.financesapp.presentation.settings.SettingsScreenComponents
import com.example.financesapp.ui.theme.Green
import com.example.financesapp.ui.theme.LightGreen

sealed class AppRoute(val route: String) {
    object Expenses : AppRoute("Расходы")
    object ExpensesHistory : AppRoute("Расходы/История")
    object ExpensesAnalysis : AppRoute("Расходы/Анализ")
    object Income : AppRoute("Доходы")
    object Account : AppRoute("Счет")
    object Articles : AppRoute("Статьи")
    object Settings : AppRoute("Настройки")

    companion object {
        fun fromString(route: String?): AppRoute? {
            return when (route) {
                Expenses.route -> Expenses
                ExpensesHistory.route -> ExpensesHistory
                ExpensesAnalysis.route -> ExpensesAnalysis
                Income.route -> Income
                Account.route -> Account
                Articles.route -> Articles
                Settings.route -> Settings
                else -> null
            }
        }
    }
}

@Composable
fun MainAppScreen() {
    val rootNavController = rememberNavController()
    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()
    val currentRoute = AppRoute.fromString(navBackStackEntry?.destination?.route)
//    val currentRoute = navBackStackEntry?.destination?.route

//    val topAppBarProviders = when (currentRoute) {
//        "Расходы" -> ExpensesTopAppBarProvider
//        "Доходы" -> IncomeTopAppBarProvider
//        "Счет" -> AccountTopAppBarProvider
//        "Статьи" -> ArticlesTopAppBarProvider
//        "Настройки" -> SettingsTopAppBarProvider
//        else -> NoTopAppBarProvider
//    }

    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    val screenComponents = remember(currentDestination?.route) {
        when (currentDestination?.route) {
            Screen.Expenses.route -> ExpensesScreenComponents()
            Screen.Income.route -> IncomeScreenComponents()
            Screen.Account.route -> AccountScreenComponents()
            Screen.Articles.route -> ArticlesScreenComponents()
            Screen.Settings.route -> SettingsScreenComponents()
            else -> null
        }
    }

    Scaffold(
        topBar = {
            screenComponents?.topAppBarProvider?.ProvideTopAppBar(navController)
        },
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
                            selectedIconColor = Green,
                            indicatorColor = LightGreen
                        )
                    )
                }
            }
//            BottomNavBar(navController, items)
        },
        floatingActionButton = {
            screenComponents?.floatingActionButtonProvider?.ProvideFloatingActionButton(navController)
        }
//        floatingActionButton = {
//            if (currentRoute == "Расходы" || currentRoute == "Доходы" || currentRoute == "Счет") {
//                FloatingActionButton(
//                    onClick = {
//                        when(currentRoute) {
//                            "Расходы" -> {}
//                            "Доходы" -> {}
//                            "Счет" -> {}
//                        }
//                    },
//                    containerColor = Green,
//                    contentColor = Color.White,
//                    shape = CircleShape
//                ) {
//                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
//                }
//            }
//        }
    ) { padding ->
        RootNavGraph(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}