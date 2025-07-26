package com.example.settings

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.common.util.LocaleHelper
import com.example.ui.FinancesTopBarConfig
import kotlinx.coroutines.delay

@Composable
internal fun LanguageSwitchScreen(
    navigateBack: () -> Unit
) {
    val viewModel: SettingsViewModel = viewModel()
    val locale by viewModel.locale.collectAsStateWithLifecycle()
    val languages =
        listOf("ru" to stringResource(R.string.russian), "en" to stringResource(R.string.english))
    val activity = LocalContext.current as? Activity
    val view = LocalView.current
    var shouldUpdateLocale by remember { mutableStateOf(false) }
    var localeToUpdate by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(shouldUpdateLocale) {
        if (shouldUpdateLocale && localeToUpdate != null) {
            delay(100)
            activity?.let {
                LocaleHelper.updateLocale(it, localeToUpdate!!)
                view.invalidate()
                view.parent?.requestLayout()
            }
            shouldUpdateLocale = false
            localeToUpdate = null
        }
    }

    FinancesTopBarConfig(
        title = { Text(stringResource(R.string.language)) },
        navAction = {
            IconButton(onClick = navigateBack) {
                Icon(
                    painterResource(com.example.ui.R.drawable.ic_back),
                    contentDescription = stringResource(R.string.back)
                )
            }
        }
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
                        if (locale != code) {
                            viewModel.setLocale(code)
                            localeToUpdate = code
                            shouldUpdateLocale = true
                        }
                    }
                )
                Text(label)
            }
        }
    }
}
