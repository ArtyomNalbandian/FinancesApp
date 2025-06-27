package com.example.financesapp.presentation.screens.articles

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.financesapp.presentation.common.FloatingActionButtonProvider
import com.example.financesapp.presentation.common.ScreenComponents
import com.example.financesapp.presentation.common.TopAppBarProvider

class ArticlesScreenComponents : ScreenComponents {
    override val topAppBarProvider: TopAppBarProvider = ArticlesTopAppBarProvider
    override val floatingActionButtonProvider: FloatingActionButtonProvider? = null
}

object ArticlesTopAppBarProvider : TopAppBarProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ProvideTopAppBar(navController: NavHostController) {
        CenterAlignedTopAppBar(
            title = { Text("Мои статьи") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}