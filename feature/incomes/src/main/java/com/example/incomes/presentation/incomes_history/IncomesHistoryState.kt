package com.example.incomes.presentation.incomes_history//package com.example.financesapp.presentation.screens.history.history_income

import com.example.common.model.income.Income

internal sealed interface IncomesHistoryState {

    data object Loading : IncomesHistoryState
    data class Content(
        val items: List<Income>,
        val total: String
    ) : IncomesHistoryState

    data class Error(val message: String) : IncomesHistoryState
}
