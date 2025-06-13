package com.example.financesapp.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financesapp.presentation.account.AccountTopAppBarProvider
import com.example.financesapp.presentation.articles.ArticlesTopAppBarProvider
import com.example.financesapp.presentation.common.NoTopAppBarProvider
import com.example.financesapp.presentation.expenses.ExpensesTopAppBarProvider
import com.example.financesapp.presentation.income.IncomeTopAppBarProvider
import com.example.financesapp.presentation.settings.SettingsTopAppBarProvider
import com.example.financesapp.ui.theme.CardItemBackground
import com.example.financesapp.ui.theme.Green

@Composable
fun MainAppScreen() {
    val rootNavController = rememberNavController()
    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val topAppBarProviders = when(currentRoute) {
        "Расходы" -> ExpensesTopAppBarProvider
        "Доходы" -> IncomeTopAppBarProvider
        "Счет" -> AccountTopAppBarProvider
        "Статьи" -> ArticlesTopAppBarProvider
        "Настройки" -> SettingsTopAppBarProvider
        else -> NoTopAppBarProvider
    }

    Scaffold(
        topBar = {
            topAppBarProviders.ProvideTopAppBar()
        },

        bottomBar = {
            BottomNavBar(rootNavController, items)
        },
        floatingActionButton = {
            if (currentRoute == "Расходы" || currentRoute == "Доходы" || currentRoute == "Счет") {
                FloatingActionButton(
                    onClick = {
                        when(currentRoute) {
                            "Расходы" -> {}
                            "Доходы" -> {}
                            "Счет" -> {}
                        }
                    },
                    containerColor = Green,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        }
    ) { padding ->
        RootNavHost(rootNavController, modifier = Modifier.background(CardItemBackground).padding(padding))
    }
}