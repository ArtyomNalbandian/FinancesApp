package com.example.financesapp.presentation.screens.edit_account

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financesapp.R
import com.example.financesapp.presentation.common.FinancesTopBarConfig

@Composable
fun EditAccountScreen(
    accountId: String,
    viewModelFactory: ViewModelProvider.Factory,
    editAccountViewModel: EditAccountViewModel = viewModel(factory = viewModelFactory),
    navigateBack: () -> Unit,
) {

    val state by editAccountViewModel.state.collectAsState()

    FinancesTopBarConfig(
        title = { Text("Мой счет") },
        actions = {
            IconButton(
                onClick = {
                    Log.d("testLog", "EditAccountScreen onClick --- accountId = $accountId")
                    editAccountViewModel.handleIntent(EditAccountIntent.Submit((state as EditAccountState.Content).account))
                    navigateBack()
                }
            ) {
                Icon(painterResource(R.drawable.ic_apply), contentDescription = "Подтвердить")
            }
        },
        navAction = {
            IconButton(onClick = navigateBack) {
                Icon(painterResource(R.drawable.ic_back), contentDescription = "Назад")
            }
        }
    )
}
