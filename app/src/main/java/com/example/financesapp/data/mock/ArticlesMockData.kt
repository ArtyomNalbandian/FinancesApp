package com.example.financesapp.data.mock

import com.example.financesapp.R

data class Articles(
    val title: String,
    val leadingIcon: Int?,
    val trailingIcon: Int? = null,
    val amount: String? = null
)

val articles = listOf(
    Articles(
        "Аренда квартиры",
        R.drawable.house_test
    ),
    Articles(
        "Одежда",
        R.drawable.dress
    ),
    Articles(
        "На собачку",
        R.drawable.dawg
    ),
    Articles(
        "На собачку",
        R.drawable.dawg
    ),
    Articles(
        "Ремонт квартиры",
        R.drawable.repair
    ),
    Articles(
        "Продукты",
        R.drawable.lollipop
    ),
    Articles(
        "Спортзал",
        R.drawable.gym
    ),
    Articles(
        "Медицина",
        R.drawable.pill
    )
)