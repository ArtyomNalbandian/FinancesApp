package com.example.financesapp.presentation.navigation

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ui.FinancesTopAppBarActions
import com.example.ui.FinancesTopAppBarNavAction
import com.example.ui.FinancesTopAppBarTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FinancesTopAppBar(navController: NavController) {

    val backStackEntry by navController.currentBackStackEntryAsState()

    CenterAlignedTopAppBar(
        navigationIcon = {
            FinancesTopAppBarNavAction(backStackEntry)
        }, title = {
            FinancesTopAppBarTitle(backStackEntry)
        }, actions = {
            FinancesTopAppBarActions(backStackEntry)
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}
