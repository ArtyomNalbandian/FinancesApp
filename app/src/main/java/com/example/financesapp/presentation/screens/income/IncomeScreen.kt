package com.example.financesapp.presentation.screens.income

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.R
import com.example.financesapp.presentation.common.AddButton
import com.example.financesapp.presentation.common.FinancesTopBarConfig

//import com.example.financesapp.presentation.common.ProvideFinancesTopAppBarActions
//import com.example.financesapp.presentation.common.ProvideFinancesTopAppBarTitle

@Composable
fun IncomeScreen(
    viewModelFactory: ViewModelProvider.Factory,
    incomeViewModel: IncomeViewModel = viewModel(factory = viewModelFactory),
    navigateToHistory: () -> Unit,
) {

    FinancesTopBarConfig(
        title = { Text("Доходы сегодня") },
        actions = {
            IconButton(onClick = navigateToHistory) {
                Icon(painterResource(R.drawable.ic_history), contentDescription = "История")
            }
        }
    )

//    ProvideFinancesTopAppBarTitle { Text("Доходы сегодня") }
//    ProvideFinancesTopAppBarActions {
//        IconButton(
//            onClick = { navigateToHistory() }
//        ) {
//            Icon(
//                painter = painterResource(R.drawable.ic_history),
//                contentDescription = "История доходов"
//            )
//        }
//    }

    val context = LocalContext.current
    val state by incomeViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        incomeViewModel.event.collect { event ->
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
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        when (val currentState = state) {
            is IncomeState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is IncomeState.Error -> {
                val error = currentState.message
                Text("Ошибка: $error", color = Color.Red)
            }

            is IncomeState.Content -> {
                IncomeScreenContent(
                    income = currentState.income,
                    amount = currentState.total,
                    currency = currentState.currency,
                    onIncomeClick = { }
                )
                AddButton(
                    onClick = { },
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}
