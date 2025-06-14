package com.example.financesapp.presentation.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financesapp.R
import com.example.financesapp.data.mock.Expenses
import com.example.financesapp.data.mock.expensesTotal
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.ui.theme.LightGreen


val history = listOf(
    Expenses(
        title = "Ремонт квартиры",
        leadingIcon = R.drawable.repair,
        supportingText = "Ремонт - фурнитура для дверей",
        amount = "100 000 ₽",
        trailingIcon = R.drawable.more_vert,
    ),
    Expenses(
        title = "На собачку",
        leadingIcon = R.drawable.dawg,
        supportingText = "Ремонт - фурнитура для дверей",
        amount = "100 000 ₽",
        trailingIcon = R.drawable.more_vert,
    ),
    Expenses(
        title = "На собачку",
        leadingIcon = R.drawable.dawg,
        amount = "100 000 ₽",
        trailingIcon = R.drawable.more_vert,
    ),
    Expenses(
        title = "На собачку",
        leadingIcon = R.drawable.dawg,
        amount = "100 000 ₽",
        trailingIcon = R.drawable.more_vert,
    ),
    Expenses(
        title = "На собачку",
        leadingIcon = R.drawable.dawg,
        amount = "100 000 ₽",
        trailingIcon = R.drawable.more_vert,
    )
)

@Composable
fun HistoryScreen() {
    Column {
        ListItem(
            title = "Начало",
            amount = expensesTotal.amount,
            backgroundColor = LightGreen,
            modifier = Modifier.height(56.dp)
        )
        ListItem(
            title = "Конец",
            amount = expensesTotal.amount,
            backgroundColor = LightGreen,
            modifier = Modifier.height(56.dp)
        )
        ListItem(
            title = "Сумма",
            amount = expensesTotal.amount,
            backgroundColor = LightGreen,
            modifier = Modifier.height(56.dp)
        )
        HorizontalDivider()
        LazyColumn {
            items(history) { history ->
                ListItem(
                    title = history.title,
                    supportingText = history.supportingText,
                    leadingIcon = history.leadingIcon,
                    trailingIcon = history.trailingIcon,
                    amount = history.amount,
                    onClick = { } //TODO
                )
                HorizontalDivider()
            }
        }
    }
}