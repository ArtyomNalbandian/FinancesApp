package com.example.financesapp.presentation.analysis

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

class AnalysisScreenComponents(analysisType: String) : ScreenComponents {
    override val topAppBarProvider: TopAppBarProvider =
        AnalysisTopAppBarProvider(analysisType = analysisType)
    override val floatingActionButtonProvider: FloatingActionButtonProvider? = null
}

class AnalysisTopAppBarProvider(private val analysisType: String) : TopAppBarProvider {
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
            }
        )
    }
}