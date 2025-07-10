//package com.example.financesapp.domain.usecases.impl
//
//import com.example.common.model.category.Category
//import com.example.financesapp.domain.usecases.interfaces.GetCategoriesUseCase
//import javax.inject.Inject
//
//class GetCategoriesUseCaseImpl @Inject constructor(
//    private val categoriesRepository: CategoriesRepository
//) : GetCategoriesUseCase {
//
//    override suspend fun invoke(): List<Category> {
//        return categoriesRepository.getCategories()
//    }
//}
