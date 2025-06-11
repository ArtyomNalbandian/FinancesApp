package com.example.financesapp.presentation.income

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.financesapp.data.mock.income
import com.example.financesapp.data.mock.incomeTotal
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.ui.theme.LightGreen

@Composable
fun IncomeScreen() {
    Column {
        ListItem(
            title = incomeTotal.title,
            amount = incomeTotal.amount,
            backgroundColor = LightGreen,
            contentPadding = PaddingValues(vertical = 16.dp)
        )
        LazyColumn {
            items(income) { income ->
                ListItem(
                    title = income.title,
                    trailingIcon = income.trailingIcon,
                    amount = income.amount,
                    onClick = {  } //TODO
                )
            }
        }
    }
}