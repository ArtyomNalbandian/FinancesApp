package com.example.financesapp.presentation.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.financesapp.R
import com.example.financesapp.presentation.navigation.ScreenRoute.AccountGraph
import com.example.financesapp.presentation.navigation.ScreenRoute.CategoriesGraph
import com.example.financesapp.presentation.navigation.ScreenRoute.ExpensesGraph
import com.example.financesapp.presentation.navigation.ScreenRoute.IncomeGraph
import com.example.financesapp.presentation.navigation.ScreenRoute.SettingsGraph

@Composable
internal fun BottomNavigationBar(
    navController: NavController,
    currentDestination: NavDestination?
) {
    NavigationBar {
        topLevelRoutes.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.name,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(item.name) },
                selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                onClick = {
                    navController.navigate(item.route) {
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
}

private val topLevelRoutes = listOf(
    TopLevelRoute("Расходы", ExpensesGraph, R.drawable.expenses),
    TopLevelRoute("Доходы", IncomeGraph, R.drawable.income),
    TopLevelRoute("Счета", AccountGraph, R.drawable.account),
    TopLevelRoute("Статьи", CategoriesGraph, R.drawable.articles),
    TopLevelRoute("Настройки", SettingsGraph, R.drawable.settings),
)

private data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: Int)
