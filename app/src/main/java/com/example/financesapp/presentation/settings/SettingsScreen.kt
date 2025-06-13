package com.example.financesapp.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
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
            title = "Темная тема",
            trailingComposable = {
                Switch(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    modifier = Modifier.clickable { isChecked = !isChecked }
                )
            },
            modifier = Modifier.height(56.dp)
        )
        HorizontalDivider()
        ListItem(
            title = "Основной цвет",
            trailingIcon = R.drawable.test_icon,
            onClick = {  }, //TODO,
            modifier = Modifier.height(56.dp)
        )
        HorizontalDivider()
        ListItem(
            title = "Звуки",
            trailingIcon = R.drawable.test_icon,
            onClick = {  }, //TODO
            modifier = Modifier.height(56.dp)
        )
        HorizontalDivider()
        ListItem(
            title = "Хаптики",
            trailingIcon = R.drawable.test_icon,
            onClick = {  }, //TODO
            modifier = Modifier.height(56.dp)
        )
        HorizontalDivider()
        ListItem(
            title = "Код пароль",
            trailingIcon = R.drawable.test_icon,
            onClick = {  }, //TODO
            modifier = Modifier.height(56.dp)
        )
        HorizontalDivider()
        ListItem(
            title = "Синхронизация",
            trailingIcon = R.drawable.test_icon,
            onClick = {  }, //TODO
            modifier = Modifier.height(56.dp)
        )
        HorizontalDivider()
        ListItem(
            title = "Язык",
            trailingIcon = R.drawable.test_icon,
            onClick = {  }, //TODO
            modifier = Modifier.height(56.dp)

        )
        HorizontalDivider()
        ListItem(
            title = "О программе",
            trailingIcon = R.drawable.test_icon,
            onClick = {  }, //TODO
            modifier = Modifier.height(56.dp)
        )
        HorizontalDivider()
    }
}