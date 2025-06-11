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
import com.example.financesapp.presentation.common.ItemCard
import com.example.financesapp.presentation.common.TestListItem
import com.example.financesapp.presentation.settings.components.SettingsItemCard
import com.example.financesapp.presentation.settings.components.SettingsSwitchThemeCard

@Composable
fun SettingsScreen() {
    Column {
        var isChecked by remember { mutableStateOf(false) }
//        ItemCard(
//            name = "Светлая/темная тема",
//            trailingComposable = {
//                Switch(
//                    checked = isChecked,
//                    onCheckedChange = { isChecked = it },
//                    modifier = Modifier.clickable { isChecked = !isChecked }
//                )
//            }
//        )
//        SettingsSwitchThemeCard()
//        SettingsItemCard(
//            name = "Основной цвет",
//            trailingIcon = R.drawable.test_icon
//        )
//        SettingsItemCard(
//            name = "Звуки",
//            trailingIcon = R.drawable.test_icon
//        )
//        SettingsItemCard(
//            name = "Хаптики",
//            trailingIcon = R.drawable.test_icon
//        )
//        SettingsItemCard(
//            name = "Код пароль",
//            trailingIcon = R.drawable.test_icon
//        )
//        SettingsItemCard(
//            name = "Синхронизация",
//            trailingIcon = R.drawable.test_icon
//        )
//        SettingsItemCard(
//            name = "Язык",
//            trailingIcon = R.drawable.test_icon
//        )
//        SettingsItemCard(
//            name = "О программе",
//            trailingIcon = R.drawable.test_icon
//        )
        TestListItem(
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
        TestListItem(
            title = "Основной цвет",
            trailingIcon = R.drawable.test_icon,
            contentPadding =  PaddingValues(vertical = 16.dp),
            onClick = {  } //TODO
        )
        TestListItem(
            title = "Звуки",
            trailingIcon = R.drawable.test_icon,
            contentPadding =  PaddingValues(vertical = 16.dp),
            onClick = {  } //TODO
        )
        TestListItem(
            title = "Хаптики",
            trailingIcon = R.drawable.test_icon,
            contentPadding =  PaddingValues(vertical = 16.dp),
            onClick = {  } //TODO
        )
        TestListItem(
            title = "Код пароль",
            trailingIcon = R.drawable.test_icon,
            contentPadding =  PaddingValues(vertical = 16.dp),
            onClick = {  } //TODO
        )
        TestListItem(
            title = "Синхронизация",
            trailingIcon = R.drawable.test_icon,
            contentPadding =  PaddingValues(vertical = 16.dp),
            onClick = {  } //TODO
        )
        TestListItem(
            title = "Язык",
            trailingIcon = R.drawable.test_icon,
            contentPadding =  PaddingValues(vertical = 16.dp),
            onClick = {  } //TODO
        )
        TestListItem(
            title = "О программе",
            trailingIcon = R.drawable.test_icon,
            contentPadding =  PaddingValues(vertical = 16.dp),
            onClick = {  } //TODO
        )
    }
}