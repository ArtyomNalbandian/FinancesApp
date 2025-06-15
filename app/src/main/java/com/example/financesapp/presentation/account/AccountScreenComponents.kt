package com.example.financesapp.presentation.account

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.financesapp.ui.theme.Green

class AccountScreenComponents : ScreenComponents {
    override val topAppBarProvider: TopAppBarProvider = AccountTopAppBarProvider
    override val floatingActionButtonProvider: FloatingActionButtonProvider =
        AccountFloatingActionButtonProvider
}

object AccountTopAppBarProvider : TopAppBarProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ProvideTopAppBar(navController: NavHostController) {
        CenterAlignedTopAppBar(
            title = { Text("Мой счет") },
            actions = {
                IconButton(onClick = {
                    //TODO()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.edit),
                        contentDescription = "Редактировать счет"
                    )
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Green
            )
        )
    }
}

object AccountFloatingActionButtonProvider : FloatingActionButtonProvider {
    @Composable
    override fun ProvideFloatingActionButton(navController: NavHostController) {
        FloatingActionButton(
            onClick = {
                //TODO()
            },
            containerColor = Green,
            contentColor = Color.White,
            shape = CircleShape,
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить счет")
        }
    }
}