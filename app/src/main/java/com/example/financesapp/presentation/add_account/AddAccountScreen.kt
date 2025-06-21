package com.example.financesapp.presentation.add_account

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.financesapp.R
import com.example.financesapp.presentation.common.TopAppBarState
import com.example.financesapp.presentation.common.TopAppBarStateProvider

@Composable
fun AddAccountScreen(
    onLeadingIconClick: () -> Unit,
    onTrailingIconClick: () -> Unit
) {
    TopAppBarStateProvider.update(
        TopAppBarState(
            title = "Новый счет",
            leadingIcon = R.drawable.ic_cancel,
            trailingIcon = R.drawable.ic_apply,
            onLeadingIconClick = onLeadingIconClick,
            onTrailingIconClick = {
                println("TestLog yooooo")
                onTrailingIconClick()
            }
        )
    )

    Text("AddAccountScreen")
}