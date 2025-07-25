package com.example.categories.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.categories.di.DaggerCategoriesComponent
import com.example.database.di.DaggerDatabaseComponent
import com.example.network.di.DaggerNetworkComponent
import com.example.ui.FinancesTopBarConfig
import com.example.categories.R.string

@Composable
internal fun CategoriesScreen() {

    val networkComponent = DaggerNetworkComponent.create()
    val databaseComponent = DaggerDatabaseComponent.builder()
        .context(LocalContext.current.applicationContext)
        .build()
    val categoriesComponent = DaggerCategoriesComponent
        .factory()
        .create(
            networkApi = networkComponent,
            databaseApi = databaseComponent
        )
    val categoriesViewModel: CategoriesViewModel =
        viewModel(factory = categoriesComponent.viewModelFactory())
    val state by categoriesViewModel.state.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    FinancesTopBarConfig(
        title = { Text(stringResource(string.my_categories)) }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        when (val currentState = state) {
            is CategoriesState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is CategoriesState.Error -> { // TODO: change to snackbar
                val error = currentState.message
                Text("${stringResource(string.error)}: $error", color = Color.Red)
            }

            is CategoriesState.Content -> {
                val categories = currentState.categories
                CategoriesScreenContent(
                    categories = categories,
                    onSearch = { query -> categoriesViewModel.searchCategories(query) }
                )
            }
        }
    }
}
