package com.example.settings

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.FinancesTopBarConfig
import com.example.ui.ListItem
import com.example.settings.R.drawable.more_settings


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
        title = { Text(stringResource(R.string.settings)) },
    )

    LazyColumn {
        item {
            ListItem(
                title = stringResource(R.string.dark_theme),
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
                title = stringResource(R.string.primary_color),
                trailingIcon = more_settings,
                onClick = navigateToColorPicker,
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
        item {
            ListItem(
                title = stringResource(R.string.haptics),
                trailingIcon = more_settings,
                onClick = navigateToHaptics,
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
        item {
            ListItem(
                title = stringResource(R.string.pin_code),
                trailingIcon = more_settings,
                onClick = navigateToPinCode,
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
        item {
            ListItem(
                title = stringResource(R.string.sync),
                trailingIcon = more_settings,
                onClick = navigateToSyncFrequency,
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
        item {
            ListItem(
                title = stringResource(R.string.language),
                trailingIcon = more_settings,
                onClick = navigateToLanguageSwitch,
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
        item {
            ListItem(
                title = stringResource(R.string.about),
                trailingIcon = more_settings,
                onClick = navigateToAppInfo,
                modifier = Modifier.height(56.dp)
            )
            HorizontalDivider()
        }
    }
}
