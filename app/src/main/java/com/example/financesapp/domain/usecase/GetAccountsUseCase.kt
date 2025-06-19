package com.example.financesapp.domain.usecase

import com.example.financesapp.domain.account.Account

interface GetAccountsUseCase {

    suspend operator fun invoke(): List<Account>

}