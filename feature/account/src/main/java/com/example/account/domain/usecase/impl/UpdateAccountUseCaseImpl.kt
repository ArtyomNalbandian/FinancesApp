package com.example.account.domain.usecase.impl

import com.example.account.domain.repository.AccountRepository
import com.example.account.domain.usecase.interfaces.UpdateAccountUseCase
import com.example.common.model.account.Account
import com.example.network.dto.account.AccountRequestDto
import javax.inject.Inject

class UpdateAccountUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository
) : UpdateAccountUseCase {

    override suspend fun invoke(
        accountId: Int,
        request: AccountRequestDto
    ): Account {
        return accountRepository.updateAccount(accountId, request)
    }
}
