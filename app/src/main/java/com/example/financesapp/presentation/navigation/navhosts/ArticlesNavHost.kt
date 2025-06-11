package com.example.financesapp.presentation.navigation.navhosts

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financesapp.presentation.articles.ArticlesScreen

@Composable
fun ArticlesNavHost() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "articles_main") {
        composable("articles_main") {
            ArticlesScreen()
        }
    }
}