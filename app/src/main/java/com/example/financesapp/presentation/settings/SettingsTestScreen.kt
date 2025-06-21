package com.example.financesapp.presentation.settings

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financesapp.R
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.presentation.common.TopAppBarState
import com.example.financesapp.presentation.common.TopAppBarStateProvider

@Composable
fun SettingsTestScreen() {
    TopAppBarStateProvider.update(
        TopAppBarState(
            title = "Настройки",
        )
    )
    val items = listOf(
        "Темная тема" to null,
        "Основной цвет" to R.drawable.test_icon,
        "Звуки" to R.drawable.test_icon,
        "Хаптики" to R.drawable.test_icon,
        "Код пароль" to R.drawable.test_icon,
        "Синхронизация" to R.drawable.test_icon,
        "Язык" to R.drawable.test_icon,
        "О программе" to R.drawable.test_icon
    )

    var isDarkTheme by remember { mutableStateOf(false) }

    LazyColumn {
        item {
            ListItem(
                title = "Темная тема",
                trailingComposable = {
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { isDarkTheme = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.secondary,
                            checkedTrackColor = MaterialTheme.colorScheme.primary,
                        )
                    )
                },
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
        itemsIndexed(items) { _, icon ->
            ListItem(
                title = icon.first,
                trailingIcon = icon.second,
                onClick = { /* TODO */ },
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
    }
}