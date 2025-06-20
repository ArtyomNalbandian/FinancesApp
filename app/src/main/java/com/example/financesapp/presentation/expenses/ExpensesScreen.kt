package com.example.financesapp.presentation.expenses

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.data.remote.RetrofitInstance
import com.example.financesapp.data.remote.repository.RemoteDataSourceImpl
import com.example.financesapp.domain.usecase.impl.GetExpensesUseCaseImpl
import com.example.financesapp.presentation.common.AddButton

@Composable
fun ExpensesScreen() {
    val repository = remember { RemoteDataSourceImpl(RetrofitInstance.api) }
    val usecase = remember { GetExpensesUseCaseImpl(repository) }
    val viewModel: ExpensesViewModel = viewModel(
        factory = ExpensesViewModelFactory(usecase)
    )

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
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
            .background(color = MaterialTheme.colorScheme.tertiary)
    ) {

        when (val currentState = state) {
            is ExpensesState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is ExpensesState.Error -> {
                val error = currentState.message
                Text("Ошибка: $error", modifier = Modifier.align(Alignment.Center))
            }

            is ExpensesState.Content -> {
                ExpensesScreenContent(
                    expenses = currentState.expenses,
                    amount = currentState.total,
                    currency = currentState.currency,
                    onExpenseClick = {}
                )
                AddButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }

}
