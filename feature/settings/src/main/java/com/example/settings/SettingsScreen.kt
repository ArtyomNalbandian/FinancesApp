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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
internal fun SettingsScreen(
    navigateToColorPicker: () -> Unit,
    navigateToHaptics: () -> Unit,
    navigateToPinCode: () -> Unit,
    navigateToSyncFrequency: () -> Unit,
    navigateToLanguageSwitch: () -> Unit,
    navigateToAppInfo: () -> Unit
) {
    val viewModel: SettingsViewModel = viewModel()
    val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()

    FinancesTopBarConfig(
        title = { Text("Настройки") },
    )

    LazyColumn {
        item {
            ListItem(
                title = "Темная тема",
                trailingComposable = {
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { viewModel.setDarkTheme(it) },
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
        item {
            ListItem(
                title = "Основной цвет",
                trailingIcon = R.drawable.more_settings,
                onClick = navigateToColorPicker,
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
        item {
            ListItem(
                title = "Хаптики",
                trailingIcon = R.drawable.more_settings,
                onClick = navigateToHaptics,
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
        item {
            ListItem(
                title = "Код пароль",
                trailingIcon = R.drawable.more_settings,
                onClick = navigateToPinCode,
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
        item {
            ListItem(
                title = "Синхронизация",
                trailingIcon = R.drawable.more_settings,
                onClick = navigateToSyncFrequency,
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
        item {
            ListItem(
                title = "Язык",
                trailingIcon = R.drawable.more_settings,
                onClick = navigateToLanguageSwitch,
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
        item {
            ListItem(
                title = "О программе",
                trailingIcon = R.drawable.more_settings,
                onClick = navigateToAppInfo,
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
    }
}
