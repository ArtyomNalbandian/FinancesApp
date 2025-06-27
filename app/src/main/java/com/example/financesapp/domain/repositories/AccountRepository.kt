package com.example.financesapp.domain.repositories

import com.example.financesapp.domain.models.account.Account

/**
 * Репозиторий для работы с счетом.
 * Предоставляет методы для доступа к данным счета.
 */
interface AccountRepository {

    /**
     * Получает данные счета.
     * @return Объект [Account] с данными счета
     */
    suspend fun getAccount(): Account
}
