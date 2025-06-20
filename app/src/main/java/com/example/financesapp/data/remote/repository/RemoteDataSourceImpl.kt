package com.example.financesapp.data.remote.repository

import com.example.financesapp.data.remote.api.ApiService
import com.example.financesapp.data.remote.models.account.AccountDto
import com.example.financesapp.data.remote.models.account.AccountHistoryResponse
import com.example.financesapp.data.remote.models.account.AccountRequest
import com.example.financesapp.data.remote.models.account.AccountResponse
import com.example.financesapp.data.remote.models.category.Category
import com.example.financesapp.data.remote.models.transaction.Transaction
import com.example.financesapp.data.remote.models.transaction.TransactionRequest
import com.example.financesapp.data.remote.models.transaction.TransactionResponseDto
import com.example.financesapp.domain.repository.RemoteDataSourceRepository

class RemoteDataSourceImpl(
    private val api: ApiService
) : RemoteDataSourceRepository {

    override suspend fun getAccount(): AccountDto {
        return api.getAccounts().first()
    }

    override suspend fun getAccountById(id: Int): AccountResponse {
        return api.getAccountById(id)
    }

    override suspend fun createAccount(request: AccountRequest): AccountDto {
        return api.createAccount(request)
    }

    override suspend fun updateAccount(id: Int, request: AccountRequest): AccountDto {
        return api.updateAccountById(id, request)
    }

    override suspend fun deleteAccount(id: Int) {
        return api.deleteAccountById(id)
    }

    override suspend fun getAccountHistory(id: Int): AccountHistoryResponse {
        return api.getAccountHistory(id)
    }

    override suspend fun getCategories(): List<Category> {
        return api.getCategories()
    }

    override suspend fun getCategoriesByType(isIncome: Boolean): List<Category> {
        return api.getCategoriesByType(isIncome)
    }

    override suspend fun createTransaction(request: TransactionRequest): Transaction {
        return api.createTransaction(request)
    }

    override suspend fun getTransactionById(id: Int): TransactionResponseDto {
        return api.getTransactionById(id)
    }

    override suspend fun updateTransaction(
        id: Int,
        request: TransactionRequest
    ): TransactionResponseDto {
        return api.updateTransaction(id, request)
    }

    override suspend fun deleteTransaction(id: Int) {
        return api.deleteTransaction(id)
    }

    override suspend fun getTransactionsByPeriod(
        startDate: String?,
        endDate: String?
    ): List<TransactionResponseDto> {
        return api.getTransactionsByPeriod(
            accountId = getAccount().id,
            startDate = startDate,
            endDate = endDate
        )
    }
}