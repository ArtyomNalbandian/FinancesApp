package com.example.expenses.presentation.expenses_add

import com.example.common.model.account.Account
import com.example.common.model.category.Category
import com.example.common.model.expense.Expense
import java.time.LocalDateTime

//internal sealed class ExpensesAddState {
//
//    data object Loading : ExpensesAddState()
//    data class Content(
//        val items: List<Expense>,
//        val total: String
//    ) : ExpensesAddState()
//
//    data class Error(val message: String) : ExpensesAddState()
//}

data class ExpensesAddState(
    val isLoading: Boolean = false,
    val error: String? = null,

    val account: Account? = null,
    val categories: List<Category> = emptyList(),

    val selectedAccount: Account? = null,
    val selectedCategory: Category? = null,
    val amount: String = "",
    val selectedDateTime: LocalDateTime = LocalDateTime.now(),
    val comment: String = "",

    val isFormValid: Boolean = false,
    val amountError: String? = null,

    val isAccountDropdownExpanded: Boolean = false,
    val isCategoryDropdownExpanded: Boolean = false,
    val isDatePickerVisible: Boolean = false,
    val isTimePickerVisible: Boolean = false,
    val isCreating: Boolean = false,
    val isCreated: Boolean = false
)
