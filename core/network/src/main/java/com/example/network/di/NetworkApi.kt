package com.example.network.di

import com.example.network.api.AccountsApi
import com.example.network.api.CategoriesApi
import com.example.network.api.TransactionApi

interface NetworkApi {

    fun categoriesApi(): CategoriesApi
    fun accountsApi(): AccountsApi
    fun transactionApi(): TransactionApi
}
