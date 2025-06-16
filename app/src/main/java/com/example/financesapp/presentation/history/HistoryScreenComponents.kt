package com.example.financesapp.presentation.history

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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

class HistoryScreenComponents(historyType: String) : ScreenComponents {
    override val topAppBarProvider: TopAppBarProvider =
        HistoryTopAppBarProvider(historyType = historyType)
    override val floatingActionButtonProvider: FloatingActionButtonProvider? = null
}

class HistoryTopAppBarProvider(private val historyType: String) : TopAppBarProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ProvideTopAppBar(navController: NavHostController) {
        CenterAlignedTopAppBar(
            title = { Text("Моя история") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Назад"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    navController.navigate(Screen.Analysis(historyType))
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_analysis),
                        contentDescription = "Анализ"
                    )
                }
            }
        )
    }
}