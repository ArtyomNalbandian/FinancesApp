package com.example.categories.api.di

import com.example.network.api.CategoriesApi

interface CategoriesDependencies {
    fun categoriesApi(): CategoriesApi
}
