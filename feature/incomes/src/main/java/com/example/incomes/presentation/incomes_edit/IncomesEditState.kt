package com.example.incomes.presentation.incomes_edit

import com.example.common.model.account.Account
import com.example.common.model.category.Category
import java.time.LocalDateTime

data class IncomesEditState(
    val transactionId: Int? = null,
    val account: Account? = null,
    val categories: List<Category> = emptyList(),
    val selectedAccount: Account? = null,
    val selectedCategory: Category? = null,
    val amount: String = "",
    val amountError: String? = null,
    val selectedDateTime: LocalDateTime = LocalDateTime.now(),
    val comment: String = "",
    val isFormValid: Boolean = false,
    val isLoading: Boolean = false,
    val isUpdating: Boolean = false,
    val isDeleting: Boolean = false,
    val isUpdated: Boolean = false,
    val isDeleted: Boolean = false,
    val error: String? = null
)
