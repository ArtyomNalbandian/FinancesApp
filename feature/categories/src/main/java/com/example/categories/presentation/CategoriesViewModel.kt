package com.example.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.categories.domain.usecase.interfaces.GetCategoriesUseCase
import com.example.common.model.category.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class CategoriesViewModel @Inject constructor(
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
