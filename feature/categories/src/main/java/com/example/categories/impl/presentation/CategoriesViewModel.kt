package com.example.categories.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.categories.api.di.CategoriesComponent
import com.example.categories.api.di.CategoriesDependencies
import com.example.categories.impl.domain.usecase.interfaces.GetCategoriesUseCase
import com.example.common.model.category.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//class CategoriesViewModelFactory(
//    private val dependencies: CategoriesDependencies
//) : ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        val component = CategoriesComponent.init(dependencies)
//        val viewModel = CategoriesViewModel(
//            component.getCategoriesUseCase()
//        )
//        component.inject(viewModel)
//        return viewModel as T
//    }
//}

class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : ViewModel() {

    private val allCategories = mutableListOf<Category>()

    private val _state = MutableStateFlow<CategoriesState>(CategoriesState.Loading)
    val state: StateFlow<CategoriesState> = _state

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = CategoriesState.Loading
            try {
                val categories = getCategoriesUseCase()
                allCategories.clear()
                allCategories.addAll(categories)
                _state.value = CategoriesState.Content(categories)
            } catch (e: Exception) {
                val message = e.message ?: "Ошибка загрузки"
                _state.value = CategoriesState.Error(message)
            }
        }
    }

    fun searchCategories(query: String) {
        val filtered = if (query.isBlank()) {
            allCategories
        } else {
            allCategories.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        _state.value = CategoriesState.Content(filtered)
    }
}
