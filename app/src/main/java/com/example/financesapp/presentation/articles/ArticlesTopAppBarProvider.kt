package com.example.financesapp.presentation.articles

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.financesapp.presentation.navigation.TopAppBarProvider
import com.example.financesapp.ui.theme.Green

object ArticlesTopAppBarProvider : TopAppBarProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ProvideTopAppBar() {
        CenterAlignedTopAppBar(
            title = { Text("Мои статьи") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Green
            )
        )
    }
}