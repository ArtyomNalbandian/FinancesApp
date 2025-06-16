package com.example.financesapp.presentation.add_account

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.financesapp.presentation.common.TopAppBarProvider

@Composable
fun AddAccountScreen(
    onLeadingIconClick: () -> Unit,
    onTrailingIconClick: () -> Unit
) {
    TopAppBarProvider.update(
        TopAppBarState(
            title = "Новый счет",
            leadingIcon = R.drawable.ic_cancel,
            trailingIcon = R.drawable.ic_apply,
            onLeadingIconClick = onLeadingIconClick,
            onTrailingIconClick = onTrailingIconClick
        )
    )
    val screenComponents = remember {
        AddAccountScreenComponents(
            onSaveClick = {
                Log.d("testLog", "snackbar bitch")
            }
        )
    }

    Text("AddAccountScreen")
}