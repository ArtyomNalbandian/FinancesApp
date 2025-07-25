package com.example.settings

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.FinancesTopBarConfig

@Composable
internal fun LanguageSwitchScreen() {
    val viewModel: SettingsViewModel = viewModel()
    val locale by viewModel.locale.collectAsStateWithLifecycle()
    val languages = listOf("ru" to stringResource(R.string.russian), "en" to stringResource(R.string.english))
    val activity = LocalContext.current as? Activity

    FinancesTopBarConfig(
        title = { Text(stringResource(R.string.language)) }
    )

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(stringResource(R.string.choose_language), style = MaterialTheme.typography.titleMedium)
        languages.forEach { (code, label) ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = locale == code,
                    onClick = {
                        viewModel.setLocale(code)
                        activity?.recreate()
                    }
                )
                Text(label)
            }
        }
    }
}
