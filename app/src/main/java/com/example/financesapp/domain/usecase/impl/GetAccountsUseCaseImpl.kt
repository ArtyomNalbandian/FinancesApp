package com.example.financesapp.domain.usecase.impl

import com.example.financesapp.domain.models.account.Account
import com.example.financesapp.domain.repositories.AccountRepository
import com.example.financesapp.domain.usecase.GetAccountsUseCase


class GetAccountsUseCaseImpl(
    private val accountRepository: AccountRepository
) : GetAccountsUseCase {

    override suspend fun invoke(): Account {
        return accountRepository.getAccount()
    }
}
