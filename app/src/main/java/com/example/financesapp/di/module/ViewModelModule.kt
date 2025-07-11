package com.example.financesapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.financesapp.presentation.screens.account.AccountViewModel
import com.example.financesapp.presentation.screens.edit_account.EditAccountViewModel
import com.example.financesapp.presentation.screens.expenses.ExpensesViewModel
import com.example.financesapp.presentation.screens.history.history_expenses.ExpensesHistoryViewModel
import com.example.financesapp.presentation.screens.history.history_income.IncomeHistoryViewModel
import com.example.financesapp.presentation.screens.income.IncomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    fun bindAccountViewModel(accountViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditAccountViewModel::class)
    fun bindEditAccountViewModel(editAccountViewModel: EditAccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesViewModel::class)
    fun bindExpensesViewModel(expensesViewModel: ExpensesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomeViewModel::class)
    fun bindIncomeViewModel(incomeViewModel: IncomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesHistoryViewModel::class)
    fun bindExpensesHistoryViewModel(expensesHistoryViewModel: ExpensesHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomeHistoryViewModel::class)
    fun bindIncomeHistoryViewModel(incomeHistoryViewModel: IncomeHistoryViewModel): ViewModel
}
