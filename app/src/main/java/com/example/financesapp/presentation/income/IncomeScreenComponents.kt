package com.example.financesapp.presentation.income

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.financesapp.R
import com.example.financesapp.presentation.common.FloatingActionButtonProvider
import com.example.financesapp.presentation.common.ScreenComponents
import com.example.financesapp.presentation.common.TopAppBarProvider
import com.example.financesapp.presentation.navigation.routes.Screen
import com.example.financesapp.ui.theme.Green

class IncomeScreenComponents : ScreenComponents {
    override val topAppBarProvider: TopAppBarProvider = IncomeTopAppBarProvider
    override val floatingActionButtonProvider: FloatingActionButtonProvider = IncomeFloatingActionButtonProvider
}

object IncomeTopAppBarProvider : TopAppBarProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ProvideTopAppBar(navController: NavHostController) {
        CenterAlignedTopAppBar(
            title = { Text("Доходы сегодня") },
            actions = {
                IconButton(onClick = {
                    navController.navigate(Screen.IncomeHistory.route)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.history),
                        contentDescription = "История доходов",
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Green
            )
        )
    }
}

object IncomeFloatingActionButtonProvider : FloatingActionButtonProvider {
    @Composable
    override fun ProvideFloatingActionButton(navController: NavHostController) {
        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.AddIncome.route)
            },
            containerColor = Green,
            contentColor = Color.White,
            shape = CircleShape,
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить доход")
        }
    }
}