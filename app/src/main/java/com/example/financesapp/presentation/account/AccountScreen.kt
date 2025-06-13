package com.example.financesapp.presentation.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            leadingIcon = R.drawable.money,
            trailingIcon = R.drawable.more_vert,
            onClick = {}, //TODO()
            modifier = Modifier.height(56.dp)
        )
        HorizontalDivider()
        ListItem(
            title = "Валюта",
            amount = "₽",
            backgroundColor = LightGreen,
            trailingIcon = R.drawable.more_vert,
            onClick = { }, //TODO()
            modifier = Modifier.height(56.dp)
        )
    }

}