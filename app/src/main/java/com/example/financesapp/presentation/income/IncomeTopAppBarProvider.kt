package com.example.financesapp.presentation.income

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.financesapp.R
import com.example.financesapp.presentation.common.TopAppBarProvider
import com.example.financesapp.ui.theme.Green

object IncomeTopAppBarProvider : TopAppBarProvider {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun ProvideTopAppBar() {
        CenterAlignedTopAppBar(
            title = { Text("Доходы сегодня") },
            actions = {
                IconButton(onClick = { }) {
                    Icon(painter = painterResource(R.drawable.history), contentDescription = "История доходов")
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Green
            )
        )
    }
}