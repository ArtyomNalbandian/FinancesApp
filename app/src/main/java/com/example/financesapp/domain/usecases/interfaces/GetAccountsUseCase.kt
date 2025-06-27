package com.example.financesapp.domain.usecases.interfaces

import com.example.financesapp.domain.models.account.Account


/**
 * UseCase для получения текущего аккаунта пользователя.
 * @return [Account] - доменная модель аккаунта
 */
interface GetAccountsUseCase {

    suspend operator fun invoke(): Account

}
