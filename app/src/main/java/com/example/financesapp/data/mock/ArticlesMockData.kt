package com.example.financesapp.data.mock

data class ArticlesMock(
    val title: String,
    val leadingIcon: String,
)

val articles = listOf(
    ArticlesMock(
        "Аренда квартиры",
        "\uD83C\uDFE1",
    ),
    ArticlesMock(
        "Одежда",
        "\uD83D\uDC57"
    ),
    ArticlesMock(
        "На собачку",
        "\uD83D\uDC36"
    ),
    ArticlesMock(
        "На собачку",
        "\uD83D\uDC36"
    ),
    ArticlesMock(
        "Ремонт квартиры",
        "\uD83C\uDFE0"
    ),
    ArticlesMock(
        "Продукты",
        "\uD83C\uDF6D"
    ),
    ArticlesMock(
        "Спортзал",
        "\uD83C\uDFCB"
    ),
    ArticlesMock(
        "Медицина",
        "\uD83D\uDC8A"
    )
)
