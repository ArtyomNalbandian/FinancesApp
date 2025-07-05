package com.example.financesapp.presentation.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.presentation.common.FinancesTopBarConfig

@Composable
fun CategoriesScreen(
    viewModelFactory: ViewModelProvider.Factory,
    categoriesViewModel: CategoriesViewModel = viewModel(factory = viewModelFactory)
) {
    val state by categoriesViewModel.state.collectAsState()
    val focusManager = LocalFocusManager.current

    FinancesTopBarConfig(
        title = { Text("Мои статьи") }
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
                Text("Ошибка: $error", color = Color.Red)
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
