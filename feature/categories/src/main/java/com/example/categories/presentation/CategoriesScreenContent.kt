package com.example.categories.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.common.model.category.Category
import com.example.ui.ListItem
import com.example.ui.SearchBar

@Composable
internal fun CategoriesScreenContent(
    categories: List<Category>,
    onSearch: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(onSearch = onSearch)
        HorizontalDivider()
        LazyColumn {
            items(categories, key = { it.id }) { category ->
                ListItem(
                    title = category.name,
                    leadingIcon = category.emoji,
                )
                HorizontalDivider()
            }
        }
    }
}
