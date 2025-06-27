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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.financesapp.R

@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentDestination: NavDestination?
) {
    NavigationBar {
        navigationItems.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.iconRes),
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(item.label) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route.route } == true,
                onClick = {
                    navController.navigate(item.route.route) {
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

private val navigationItems = listOf(
    NavigationItem(ScreenRoute.ExpensesGraph, R.drawable.expenses, "Расходы"),
    NavigationItem(ScreenRoute.IncomeGraph, R.drawable.income, "Доходы"),
    NavigationItem(ScreenRoute.AccountGraph, R.drawable.account, "Счет"),
    NavigationItem(ScreenRoute.ArticlesGraph, R.drawable.articles, "Статьи"),
    NavigationItem(ScreenRoute.SettingsGraph, R.drawable.settings, "Настройки")
)

data class NavigationItem(
    val route: ScreenRoute,
    val iconRes: Int,
    val label: String
)
