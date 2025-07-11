package com.example.settings

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.FinancesTopBarConfig
import com.example.ui.ListItem
import com.example.ui.R

@Composable
internal fun SettingsScreen() {

    FinancesTopBarConfig(
        title = { Text("Настройки") },
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
        itemsIndexed(settingsItems) { _, icon ->
            ListItem(
                title = icon.first,
                trailingIcon = icon.second,
                onClick = { },
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
    }
}

private val settingsItems = listOf(
    "Основной цвет" to R.drawable.more_settings,
    "Звуки" to R.drawable.more_settings,
    "Хаптики" to R.drawable.more_settings,
    "Код пароль" to R.drawable.more_settings,
    "Синхронизация" to R.drawable.more_settings,
    "Язык" to R.drawable.more_settings,
    "О программе" to R.drawable.more_settings
)
