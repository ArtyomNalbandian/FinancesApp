package com.example.financesapp.presentation.common

data class TopAppBarState(
    val title: String? = null,
    val leadingIcon: Int? = null,
    val trailingIcon: Int? = null,
    val onLeadingIconClick: (() -> Unit)? = null,
    val onTrailingIconClick: (() -> Unit)? = null,
)
