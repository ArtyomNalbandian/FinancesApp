package com.example.financesapp.domain.usecases.interfaces

import com.example.financesapp.domain.models.account.Account

interface GetAccountsUseCase {

    suspend operator fun invoke(): Account
}
