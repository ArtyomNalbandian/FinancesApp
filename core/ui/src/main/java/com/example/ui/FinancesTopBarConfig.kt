package com.example.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.LocalOwnersProvider

@Composable
fun FinancesTopAppBarNavAction(backStackEntry: NavBackStackEntry?) {

    val stateHolder = rememberSaveableStateHolder()
    var currentContent by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }

    backStackEntry?.LocalOwnersProvider(stateHolder) {
        val actionViewModel = viewModel<FinancesTopAppBarViewModel>()
        val newContent = actionViewModel.navActionState

        if (newContent != null) {
            currentContent = newContent
        }

        currentContent?.let { it() }
    } ?: currentContent?.let { it() }
}

@Composable
fun RowScope.FinancesTopAppBarActions(backStackEntry: NavBackStackEntry?) {

    val stateHolder = rememberSaveableStateHolder()
    var currentContent by remember { mutableStateOf<(@Composable RowScope.() -> Unit)?>(null) }

    backStackEntry?.LocalOwnersProvider(stateHolder) {
        val actionViewModel = viewModel<FinancesTopAppBarViewModel>()
        val newContent = actionViewModel.actionState

        if (newContent != null) {
            currentContent = newContent
        }

        currentContent?.invoke(this)
    } ?: currentContent?.invoke(this)
}

@Composable
fun FinancesTopAppBarTitle(backStackEntry: NavBackStackEntry?) {

    val stateHolder = rememberSaveableStateHolder()
    var currentContent by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }

    backStackEntry?.LocalOwnersProvider(stateHolder) {
        val actionViewModel = viewModel<FinancesTopAppBarViewModel>()
        val newContent = actionViewModel.titleState

        if (newContent != null) {
            currentContent = newContent
        }

        currentContent?.let { it() }
    } ?: currentContent?.let { it() }
}

@Composable
fun FinancesTopBarConfig(
    title: (@Composable () -> Unit)? = {},
    navAction: (@Composable () -> Unit)? = {},
    actions: (@Composable RowScope.() -> Unit)? = {},
) {

    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current)
    val viewModel = viewModel<FinancesTopAppBarViewModel>(viewModelStoreOwner = viewModelStoreOwner)

    SideEffect {
        viewModel.setTopBarContent(title, navAction, actions)
    }
}

class FinancesTopAppBarViewModel : ViewModel() {

    var titleState by mutableStateOf<(@Composable () -> Unit)?>(null, referentialEqualityPolicy())
        private set

    var navActionState by mutableStateOf<(@Composable () -> Unit)?>(null, referentialEqualityPolicy())
        private set

    var actionState by mutableStateOf<(@Composable RowScope.() -> Unit)?>(null, referentialEqualityPolicy())
        private set

    fun setTopBarContent(
        title: (@Composable () -> Unit)?,
        navAction: (@Composable () -> Unit)?,
        actions: (@Composable RowScope.() -> Unit)?
    ) {
        titleState = title
        navActionState = navAction
        actionState = actions
    }
}
