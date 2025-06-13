package com.example.financesapp.presentation.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.financesapp.R
import com.example.financesapp.ui.theme.Green
import com.example.financesapp.ui.theme.LightGreen

data class BottomNavigationItem(
    val title: String,
    val icon: Int
)

val items = listOf(
    BottomNavigationItem("Расходы", R.drawable.expenses),
    BottomNavigationItem("Доходы", R.drawable.income),
    BottomNavigationItem("Счет", R.drawable.account),
    BottomNavigationItem("Статьи", R.drawable.articles_new),
    BottomNavigationItem("Настройки", R.drawable.settings),
)

@Composable
fun BottomNavBar(
    rootNavController: NavController,
    items: List<BottomNavigationItem>
) {
    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            val isSelected = item.title.equals(currentRoute, ignoreCase = true)

            NavigationBarItem(
                selected = isSelected,
                label = { Text(text = item.title) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        modifier = Modifier
                            .size(24.dp)
                    )
                },
                onClick = {
                    rootNavController.navigate(item.title) {
                        popUpTo(rootNavController.graph.findStartDestination().id) {
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
}