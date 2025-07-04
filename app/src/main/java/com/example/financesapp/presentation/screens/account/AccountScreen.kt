package com.example.financesapp.presentation.screens.account

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.R
import com.example.financesapp.domain.models.account.Account
import com.example.financesapp.presentation.common.FinancesTopBarConfig

@Composable
fun AccountScreen(
    viewModelFactory: ViewModelProvider.Factory,
    accountViewModel: AccountViewModel = viewModel(factory = viewModelFactory),
    navigateToEditAccount: (Account) -> Unit
) {

    val state by accountViewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val currentAccount by accountViewModel.selectedAccount.collectAsStateWithLifecycle()
    Log.d("testLog", "currAcc --- $currentAccount")

    FinancesTopBarConfig(
        title = { Text("Мой счет") },
        actions = {
            IconButton(
                onClick = {
                    if (state is AccountState.Content) {
                        Log.d("testLog", "if scope")
                        navigateToEditAccount((state as AccountState.Content).account)
                    } else {
                        Log.d("testLog", "else scope")
                        // TODO() показать snackbar с текстом "Подождите пока загрузится ваш счет"
                    }
                }
            ) {
                Icon(painterResource(R.drawable.ic_edit), contentDescription = "Редактировать счет")
            }
        }
    )

    LaunchedEffect(Unit) {
        accountViewModel.loadAccount()
        accountViewModel.event.collect { event ->
            when (event) {
                is AccountEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    // TODO() заменить на snackbar с нормальным сообщением
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val currentState = state) {
            is AccountState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is AccountState.Error -> {
                val error = currentState.message
                Text("Ошибка: $error", modifier = Modifier.align(Alignment.Center))
            }

            is AccountState.Content -> {
                AccountScreenContent(
                    account = currentAccount ?: currentState.account
                )
            }
        }
    }
}
