package com.example.financesapp.domain.usecase

import com.example.financesapp.domain.models.account.Account


/**
 * UseCase для получения текущего аккаунта пользователя.
 * @return [Account] - доменная модель аккаунта
 */
interface GetAccountsUseCase {

    suspend operator fun invoke(): Account

}
