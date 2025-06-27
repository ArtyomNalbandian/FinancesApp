package com.example.financesapp.presentation.screens.expenses

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.presentation.common.AddButton


@Composable
fun ExpensesScreen(
    viewModelFactory: ViewModelProvider.Factory,
    expensesViewModel: ExpensesViewModel = viewModel(factory = viewModelFactory)
) {

    val context = LocalContext.current
    val state by expensesViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        expensesViewModel.event.collect { event ->
            when (event) {
                is ExpensesEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        when (state) {
            is ExpensesState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is ExpensesState.Error -> {
                val error = (state as ExpensesState.Error).message
                Text("Ошибка: $error", color = Color.Red)
            }

            is ExpensesState.Content -> {
                val content = state as ExpensesState.Content
                ExpensesScreenContent(
                    expenses = content.expenses,
                    amount = content.total,
                    currency = content.currency,
                    onExpenseClick = {})
                AddButton(
                    onClick = {}, modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}
