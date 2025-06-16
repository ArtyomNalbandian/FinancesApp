package com.example.financesapp.presentation.expenses

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.financesapp.R
import com.example.financesapp.presentation.common.FloatingActionButtonProvider
import com.example.financesapp.presentation.common.ScreenComponents
import com.example.financesapp.presentation.common.TopAppBarProvider
import com.example.financesapp.presentation.navigation.Screen

class ExpensesScreenComponents : ScreenComponents {
    override val topAppBarProvider: TopAppBarProvider = ExpensesTopAppBarProvider
    override val floatingActionButtonProvider: FloatingActionButtonProvider = ExpensesFloatingActionButtonProvider
}

object ExpensesTopAppBarProvider : TopAppBarProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ProvideTopAppBar(navController: NavHostController) {
        CenterAlignedTopAppBar(
            title = { Text("Расходы сегодня") },
            actions = {
                IconButton(onClick = {
//                    navController.navigate(Screen.ExpensesHistory.route)
                    navController.navigate(Screen.History("expenses").route)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_history),
                        contentDescription = "История расходов",
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

object ExpensesFloatingActionButtonProvider : FloatingActionButtonProvider {
    @Composable
    override fun ProvideFloatingActionButton(navController: NavHostController) {
        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.AddExpense.route)
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.surface,
            shape = CircleShape,
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить расход")
        }
    }
}