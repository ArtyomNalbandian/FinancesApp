package com.example.financesapp.presentation.income

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
import com.example.financesapp.domain.usecase.impl.GetIncomesUseCaseImpl
import com.example.financesapp.presentation.common.AddButton

@Composable
fun IncomeScreen() {

    val repository = remember { RemoteDataSourceImpl(RetrofitInstance.api) }
    val usecase = remember { GetIncomesUseCaseImpl(repository) }
    val viewModel: IncomeViewModel = viewModel(
        factory = IncomeViewModelFactory(usecase)
    )

    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is IncomeEvent.ShowError -> {
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

        when (state) {
            is IncomeState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is IncomeState.Error -> {
                val error = (state as IncomeState.Error).message
                Text("Ошибка: $error", modifier = Modifier.align(Alignment.Center))
            }

            is IncomeState.Content -> {
                val content = state as IncomeState.Content
                IncomeScreenContent(
                    income = content.income,
                    amount = content.total,
                    currency = content.currency,
                    onIncomeClick = {}
                )
                AddButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}
