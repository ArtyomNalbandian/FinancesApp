package com.example.financesapp.presentation.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.LocalOwnersProvider
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun ProvideFinancesTopAppBarNavAction(action : @Composable () -> Unit) {
    if (LocalViewModelStoreOwner.current == null || LocalViewModelStoreOwner.current !is NavBackStackEntry)
        return
    val actionViewModel = viewModel(initializer = { FinancesTopAppBarViewModel() })
    SideEffect {
        actionViewModel.navActionState = action
    }
}

@Composable
fun ProvideFinancesTopAppBarActions(actions: @Composable RowScope.() -> Unit) {
    if (LocalViewModelStoreOwner.current == null || LocalViewModelStoreOwner.current !is NavBackStackEntry)
        return
    val actionViewModel = viewModel(initializer = { FinancesTopAppBarViewModel() })
    SideEffect {
        actionViewModel.actionState = actions
    }
}

@Composable
fun ProvideFinancesTopAppBarTitle(title: @Composable () -> Unit) {
    if (LocalViewModelStoreOwner.current == null || LocalViewModelStoreOwner.current !is NavBackStackEntry)
        return
    val actionViewModel = viewModel(initializer = { FinancesTopAppBarViewModel() })
    SideEffect {
        actionViewModel.titleState = title
    }
}

@Composable
fun FinancesTopAppBarNavAction(backStackEntry: NavBackStackEntry?) {
    val stateHolder = rememberSaveableStateHolder()
    backStackEntry?.LocalOwnersProvider(stateHolder) {
        val actionViewModel = viewModel(initializer = { FinancesTopAppBarViewModel() })
        actionViewModel.navActionState?.let { content -> content() }
    }
}

@Composable
fun RowScope.FinancesTopAppBarActions(backStackEntry: NavBackStackEntry?) {
    val stateHolder = rememberSaveableStateHolder()
    backStackEntry?.LocalOwnersProvider(stateHolder) {
        val actionViewModel = viewModel(initializer = { FinancesTopAppBarViewModel() })
        actionViewModel.actionState?.let { content -> content() }
    }
}

@Composable
fun FinancesTopAppBarTitle(backStackEntry: NavBackStackEntry?) {
    val stateHolder = rememberSaveableStateHolder()
    backStackEntry?.LocalOwnersProvider(stateHolder) {
        val actionViewModel = viewModel(initializer = { FinancesTopAppBarViewModel() })
        actionViewModel.titleState?.let { content -> content() }
    }
}

private class FinancesTopAppBarViewModel : ViewModel() {
    var titleState by mutableStateOf(null as (@Composable () -> Unit)?, referentialEqualityPolicy())
    var navActionState by mutableStateOf(null as (@Composable () -> Unit)?, referentialEqualityPolicy())
    var actionState by mutableStateOf(null as (@Composable RowScope.() -> Unit)?, referentialEqualityPolicy())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancesTopAppBar(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    CenterAlignedTopAppBar(
        navigationIcon = {
            FinancesTopAppBarNavAction(backStackEntry)
        },
        title = {
            FinancesTopAppBarTitle(backStackEntry)
        },
        actions = {
            FinancesTopAppBarActions(backStackEntry)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}
