package com.example.financesapp.presentation.income

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financesapp.data.mock.income
import com.example.financesapp.data.mock.incomeTotal
import com.example.financesapp.presentation.common.ListItem

@Composable
fun IncomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            ListItem(
                title = incomeTotal.title,
                amount = incomeTotal.amount,
                backgroundColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
            LazyColumn {
                items(income) { income ->
                    ListItem(
                        title = income.title,
                        trailingIcon = income.trailingIcon,
                        amount = income.amount,
                        onClick = { } //TODO
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}