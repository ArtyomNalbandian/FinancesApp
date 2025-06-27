package com.example.financesapp.presentation.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.financesapp.R
import com.example.financesapp.presentation.common.TopAppBarState

fun provideTopAppBarState(
    currentBackStackEntry: NavBackStackEntry?,
    navController: NavController,
): TopAppBarState {
    return when (currentBackStackEntry?.destination?.route) {
        ScreenRoute.Expenses.route -> TopAppBarState(
            title = "Расходы сегодня",
            trailingIcon = R.drawable.ic_history,
            onTrailingIconClick = { navController.navigate(ScreenRoute.History("expenses").route) }
        )

        ScreenRoute.History("expenses").route -> TopAppBarState(
            title = "Моя история",
            leadingIcon = R.drawable.ic_back,
            trailingIcon = R.drawable.ic_analysis,
            onLeadingIconClick = { navController.popBackStack() },
            onTrailingIconClick = { }
        )

        ScreenRoute.Income.route -> TopAppBarState(
            title = "Доходы сегодня",
            trailingIcon = R.drawable.ic_history,
            onTrailingIconClick = { navController.navigate(ScreenRoute.History("income").route) }
        )

        ScreenRoute.History("income").route -> TopAppBarState(
            title = "Моя история",
            leadingIcon = R.drawable.ic_back,
            trailingIcon = R.drawable.ic_analysis,
            onLeadingIconClick = { navController.popBackStack() },
            onTrailingIconClick = { }
        )

        ScreenRoute.Account.route -> TopAppBarState(
            title = "Мой счет",
            trailingIcon = R.drawable.ic_edit,
            onTrailingIconClick = { }
        )

        ScreenRoute.Articles.route -> TopAppBarState(title = "Мои статьи")

        ScreenRoute.Account.route -> TopAppBarState(title = "Настройки")

        else -> TopAppBarState()
    }
}