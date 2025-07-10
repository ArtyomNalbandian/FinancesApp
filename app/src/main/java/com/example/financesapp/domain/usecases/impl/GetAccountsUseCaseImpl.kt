package com.example.financesapp.domain.usecases.impl

import com.example.common.model.account.Account
import com.example.financesapp.domain.repositories.AccountRepository
import com.example.financesapp.domain.usecases.interfaces.GetAccountsUseCase
import javax.inject.Inject

/**
 * Реализация [GetAccountsUseCase] для получения данных счета.
 * Делегирует вызов к [AccountRepository]
 * @property accountRepository Репозиторий для работы с данными счета [AccountRepository]
 * @constructor Создает UseCase с внедренным репозиторием
 * @param accountRepository Источник данных счета
 */
class GetAccountsUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository
) : GetAccountsUseCase {

    override suspend fun invoke(): Account {
        return accountRepository.getAccount()
    }
}
