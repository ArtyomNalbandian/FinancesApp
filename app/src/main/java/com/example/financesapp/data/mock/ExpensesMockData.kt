package com.example.financesapp.data.mock

import com.example.financesapp.R

data class Expenses(
    val title: String,
    val supportingText: String? = null,
    val leadingIcon: Int? = null,
    val trailingIcon: Int? = null,
    val amount: String? = null
)

val expensesTotal = Expenses(
    title = "Всего",
    amount = "436 558 ₽"
)

val expenses = listOf(
    Expenses(
        title = "Аренда квартиры",
        leadingIcon = R.drawable.house,
        amount = "100 000 ₽",
        trailingIcon = R.drawable.more_vert,
    ),
    Expenses(
        title = "Одежда",
        leadingIcon = R.drawable.dress,
        amount = "100 000 ₽",
        trailingIcon = R.drawable.more_vert,
    ),
    Expenses(
        title = "На собачку",
        supportingText = "Джек",
        leadingIcon = R.drawable.dawg,
        amount = "100 000 ₽",
        trailingIcon = R.drawable.more_vert,
    ),
    Expenses(
        title = "На собачку",
        supportingText = "Энни",
        leadingIcon = R.drawable.dawg,
        amount = "100 000 ₽",
        trailingIcon = R.drawable.more_vert,
    ),
    Expenses(
        title = "Ремонт квартиры",
        leadingIcon = R.drawable.repair,
        amount = "100 000 ₽",
        trailingIcon = R.drawable.more_vert,
    ),
    Expenses(
        title = "Продукты",
        leadingIcon = R.drawable.lollipop,
        amount = "100 000 ₽",
        trailingIcon = R.drawable.more_vert,
    ),
    Expenses(
        title = "Спортзал",
        leadingIcon = R.drawable.gym,
        amount = "100 000 ₽",
        trailingIcon = R.drawable.more_vert,
    ),
    Expenses(
        title = "Медицина",
        leadingIcon = R.drawable.pill,
        amount = "100 000 ₽",
        trailingIcon = R.drawable.more_vert,
    )
)