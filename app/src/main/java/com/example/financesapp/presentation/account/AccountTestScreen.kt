package com.example.financesapp.presentation.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financesapp.R
import com.example.financesapp.presentation.common.AddButton
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.presentation.common.TopAppBarState
import com.example.financesapp.presentation.common.TopAppBarStateProvider

@Composable
fun AccountTestScreen(
    onTrailingIconClick: () -> Unit,
    onAddAccount: () -> Unit,
) {
    TopAppBarStateProvider.update(
        TopAppBarState(
            title = "Мой счет",
            trailingIcon = R.drawable.ic_edit,
            onTrailingIconClick = onTrailingIconClick
        )
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            ListItem(
                title = "Баланс",
                amount = "-670 000 ₽",
                backgroundColor = MaterialTheme.colorScheme.secondary,
                leadingIcon = R.drawable.money,
                trailingIcon = R.drawable.more_vert,
                onClick = {}, //TODO()
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
            ListItem(
                title = "Валюта",
                amount = "₽",
                backgroundColor = MaterialTheme.colorScheme.secondary,
                trailingIcon = R.drawable.more_vert,
                onClick = { }, //TODO()
                modifier = Modifier.height(56.dp)
            )
        }
        AddButton(
            onClick = onAddAccount,
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}