package com.example.financesapp.presentation.income

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.data.remote.RetrofitInstance
import com.example.financesapp.data.remote.repository.RemoteDataSourceImpl
import com.example.financesapp.domain.usecase.impl.GetIncomesUseCaseImpl
import com.example.financesapp.presentation.common.AddButton
import com.example.financesapp.utils.NetworkMonitor


@Composable
fun IncomeScreen() {

    val context = LocalContext.current
    val networkMonitor = remember { NetworkMonitor(context.applicationContext) }

    val repository = remember { RemoteDataSourceImpl(RetrofitInstance.api) }
    val usecase = remember { GetIncomesUseCaseImpl(repository) }
    val viewModel: IncomeViewModel = viewModel(
        factory = IncomeViewModelFactory(usecase, networkMonitor)
    )

    val state by viewModel.state.collectAsState()
    val isConnected by networkMonitor.isConnected.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is IncomeEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val showOfflineBanner = !isConnected && state !is IncomeState.Content

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        if (showOfflineBanner) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red)
                    .height(56.dp)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Нет подключения к интернету",
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = { viewModel.retryLoad() }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Обновить",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Обновить", color = Color.White)
                }
            }
        }

        when (state) {
            is IncomeState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is IncomeState.Error -> {
                val error = (state as IncomeState.Error).message
                    Text("Ошибка: $error", color = Color.Red)
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






//@Composable
//fun IncomeScreen(networkMonitor: NetworkMonitor) {
//
////    val repository = remember { RemoteDataSourceImpl(RetrofitInstance.api) }
////    val usecase = remember { GetIncomesUseCaseImpl(repository) }
////    val viewModel: IncomeViewModel = viewModel(
////        factory = IncomeViewModelFactory(usecase, networkMonitor)
////    )
//    val context = LocalContext.current
//
//    val networkMonitor1 = remember { NetworkMonitor(context.applicationContext) }
//
//    val repository = remember { RemoteDataSourceImpl(RetrofitInstance.api) }
//    val usecase = remember { GetIncomesUseCaseImpl(repository) }
//    val viewModel: IncomeViewModel = viewModel(
//        factory = IncomeViewModelFactory(usecase, networkMonitor1)
//    )
//
//
//    val state by viewModel.state.collectAsState()
//
//    LaunchedEffect(Unit) {
//        viewModel.event.collect { event ->
//            when (event) {
//                is IncomeEvent.ShowError -> {
//                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = MaterialTheme.colorScheme.tertiary)
//    ) {
//
//        when (state) {
//            is IncomeState.Loading -> {
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//            }
//
//            is IncomeState.Error -> {
//                val error = (state as IncomeState.Error).message
//                Text("Ошибка: $error", modifier = Modifier.align(Alignment.Center))
//            }
//
//            is IncomeState.Content -> {
//                val content = state as IncomeState.Content
//                IncomeScreenContent(
//                    income = content.income,
//                    amount = content.total,
//                    currency = content.currency,
//                    onIncomeClick = {}
//                )
//                AddButton(
//                    onClick = {},
//                    modifier = Modifier.align(Alignment.BottomEnd)
//                )
//            }
//        }
//    }
//}
