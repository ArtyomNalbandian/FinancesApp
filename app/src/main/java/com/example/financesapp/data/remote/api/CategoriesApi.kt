package com.example.financesapp.data.remote.api

import com.example.financesapp.data.remote.models.category.CategoryDto
import retrofit2.http.GET

interface CategoriesApi {

    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>
}
