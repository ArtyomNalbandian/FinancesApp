package com.example.financesapp.data.mock

import com.example.financesapp.R

data class Income(
    val title: String,
    val leadingIcon: Int? = null,
    val trailingIcon: Int? = null,
    val amount: String? = null
)

val incomeTotal = Income(
    title = "Всего",
    amount = "600 000 ₽"
)

val income = listOf(
    Income(
        title = "Зарплата",
        trailingIcon = R.drawable.more_vert,
        amount = "500 000 ₽"
    ),
    Income(
        title = "Подработка",
        trailingIcon = R.drawable.more_vert,
        amount = "100 000 ₽"
    )
)