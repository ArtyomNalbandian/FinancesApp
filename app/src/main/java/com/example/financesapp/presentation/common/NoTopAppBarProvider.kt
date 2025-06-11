package com.example.financesapp.presentation.common

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.example.financesapp.presentation.navigation.TopAppBarProvider

object NoTopAppBarProvider : TopAppBarProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ProvideTopAppBar() {

    }
}