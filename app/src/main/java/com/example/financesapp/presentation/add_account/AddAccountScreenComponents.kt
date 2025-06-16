package com.example.financesapp.presentation.add_account

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

class AddAccountScreenComponents(
    private val onSaveClick: () -> Unit
) : ScreenComponents {
    override val topAppBarProvider: TopAppBarProvider = AddAccountTopAppBarProvider(onSaveClick)
    override val floatingActionButtonProvider: FloatingActionButtonProvider? = null
}

class AddAccountTopAppBarProvider(
    private val onSaveClick: () -> Unit
) : TopAppBarProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ProvideTopAppBar(navController: NavHostController) {
        CenterAlignedTopAppBar(
            title = { Text("Новый счет") },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = "Назад"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    onSaveClick
//                    navController.popBackStack()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_apply),
                        contentDescription = "Анализ"
                    )
                }
            }
        )
    }
}