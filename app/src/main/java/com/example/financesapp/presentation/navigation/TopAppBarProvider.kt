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
        Screen.Expenses.route -> TopAppBarState(
            title = "Расходы сегодня",
            trailingIcon = R.drawable.ic_history,
            onTrailingIconClick = { navController.navigate(Screen.History("expenses").route) }
        )
        Screen.History("expenses").route -> TopAppBarState(
            title = "Моя история",
            leadingIcon = R.drawable.ic_back,
            trailingIcon = R.drawable.ic_analysis,
            onLeadingIconClick = { navController.popBackStack() },
            onTrailingIconClick = { navController.navigate(Screen.Analysis("expenses").route) }
        )
        Screen.Analysis("expenses").route -> TopAppBarState(
            title = "Анализ расходов",
            leadingIcon = R.drawable.ic_back,
            onLeadingIconClick = { navController.popBackStack() }
        )
        Screen.AddExpense.route -> TopAppBarState(
            title = "Новый расход",
            leadingIcon = R.drawable.ic_cancel,
            trailingIcon = R.drawable.ic_apply,
            onLeadingIconClick = { navController.popBackStack() },
            onTrailingIconClick = { navController.navigate(Screen.Expenses.route) }
        )

        Screen.Income.route -> TopAppBarState(
            title = "Доходы сегодня",
            trailingIcon = R.drawable.ic_history,
            onTrailingIconClick = { navController.navigate(Screen.History("income").route) }
        )
        Screen.History("income").route -> TopAppBarState(
            title = "Моя история",
            leadingIcon = R.drawable.ic_back,
            trailingIcon = R.drawable.ic_analysis,
            onLeadingIconClick = { navController.popBackStack() },
            onTrailingIconClick = { navController.navigate(Screen.Analysis("income").route) }
        )
        Screen.Analysis("income").route -> TopAppBarState(
            title = "Анализ доходов",
            leadingIcon = R.drawable.ic_back,
            onLeadingIconClick = { navController.popBackStack() }
        )
        Screen.AddExpense.route -> TopAppBarState(
            title = "Новый доход",
            leadingIcon = R.drawable.ic_cancel,
            trailingIcon = R.drawable.ic_apply,
            onLeadingIconClick = { navController.popBackStack() },
            onTrailingIconClick = { navController.navigate(Screen.Income.route) }
        )

        Screen.Account.route -> TopAppBarState(
            title = "Мой счет",
            trailingIcon = R.drawable.ic_edit,
            onTrailingIconClick = { navController.navigate(Screen.EditAccount.route) }
        )
        Screen.EditAccount.route -> TopAppBarState(
            title = "Мой счет",
            leadingIcon = R.drawable.ic_back,
            trailingIcon = R.drawable.ic_apply,
            onLeadingIconClick = { navController.popBackStack() },
            onTrailingIconClick = { navController.navigate(Screen.Account.route) }
        )
        Screen.AddAccount.route -> TopAppBarState(
            title = "Новый счет",
            leadingIcon = R.drawable.ic_back,
            trailingIcon = R.drawable.ic_apply,
            onLeadingIconClick = { navController.popBackStack() },
            onTrailingIconClick = { navController.navigate(Screen.Account.route) }
        )

        Screen.Articles.route -> TopAppBarState(title = "Мои статьи")

        Screen.Account.route -> TopAppBarState(title = "Настройки")

        else -> TopAppBarState()
    }
}