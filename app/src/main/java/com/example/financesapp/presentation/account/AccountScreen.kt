package com.example.financesapp.presentation.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.financesapp.R
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.ui.theme.LightGreen

@Composable
fun AccountScreen() {
    Column {
        ListItem(
            title = "Баланс",
            amount = "-670 000 ₽",
            backgroundColor = LightGreen,
            contentPadding = PaddingValues(vertical = 16.dp),
            trailingIcon = R.drawable.more_vert
        )
        ListItem(
            title = "Валюта",
            amount = "₽",
            backgroundColor = LightGreen,
            contentPadding = PaddingValues(vertical = 16.dp),
            trailingIcon = R.drawable.more_vert
        )
    }

}