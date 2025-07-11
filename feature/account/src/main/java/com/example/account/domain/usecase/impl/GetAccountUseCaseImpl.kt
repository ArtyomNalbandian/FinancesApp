package com.example.account.domain.usecase.impl

import com.example.account.domain.repository.AccountRepository
import com.example.account.domain.usecase.interfaces.GetAccountUseCase
import com.example.common.model.account.Account
import javax.inject.Inject

/**
 * Реализация [GetAccountUseCase] для получения данных счета.
 * Делегирует вызов к [AccountRepository]
 * @property accountRepository Репозиторий для работы с данными счета [AccountRepository]
 * @constructor Создает UseCase с внедренным репозиторием
 * @param accountRepository Источник данных счета
 */
class GetAccountUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository
) : GetAccountUseCase {

    override suspend fun invoke(): Account {
        return accountRepository.getAccount()
    }
}
