package com.example.network.api

import com.example.network.dto.category.CategoryDto
import com.example.network.util.ApiUrls.CATEGORIES
import retrofit2.http.GET

interface CategoriesApi {
    @GET(CATEGORIES)
    suspend fun getCategories(): List<CategoryDto>
}
