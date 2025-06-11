package com.example.financesapp.presentation.common

import androidx.compose.runtime.Composable
import com.example.financesapp.presentation.navigation.TopAppBarProvider

object NoTopAppBarProvider : TopAppBarProvider {
    @Composable
    override fun ProvideTopAppBar() {}
}