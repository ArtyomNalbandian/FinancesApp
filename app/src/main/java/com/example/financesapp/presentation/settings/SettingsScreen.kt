package com.example.financesapp.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financesapp.R
import com.example.financesapp.presentation.common.ListItem

@Composable
fun SettingsScreen() {
    Column {
        var isChecked by remember { mutableStateOf(false) }
        ListItem(
            title = "Светлая/темная тема",
            trailingComposable = {
                Switch(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    modifier = Modifier.clickable { isChecked = !isChecked }
                )
            },
            contentPadding =  PaddingValues(vertical = 2.dp)
        )
        ListItem(
            title = "Основной цвет",
            trailingIcon = R.drawable.test_icon,
            contentPadding =  PaddingValues(vertical = 16.dp),
            onClick = {  } //TODO
        )
        ListItem(
            title = "Звуки",
            trailingIcon = R.drawable.test_icon,
            contentPadding =  PaddingValues(vertical = 16.dp),
            onClick = {  } //TODO
        )
        ListItem(
            title = "Хаптики",
            trailingIcon = R.drawable.test_icon,
            contentPadding =  PaddingValues(vertical = 16.dp),
            onClick = {  } //TODO
        )
        ListItem(
            title = "Код пароль",
            trailingIcon = R.drawable.test_icon,
            contentPadding =  PaddingValues(vertical = 16.dp),
            onClick = {  } //TODO
        )
        ListItem(
            title = "Синхронизация",
            trailingIcon = R.drawable.test_icon,
            contentPadding =  PaddingValues(vertical = 16.dp),
            onClick = {  } //TODO
        )
        ListItem(
            title = "Язык",
            trailingIcon = R.drawable.test_icon,
            contentPadding =  PaddingValues(vertical = 16.dp),
            onClick = {  } //TODO
        )
        ListItem(
            title = "О программе",
            trailingIcon = R.drawable.test_icon,
            contentPadding =  PaddingValues(vertical = 16.dp),
            onClick = {  } //TODO
        )
    }
}