package com.example.financesapp.presentation.articles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.financesapp.data.mock.articles
import com.example.financesapp.presentation.common.ItemCard
import com.example.financesapp.presentation.common.SearchBar
import com.example.financesapp.presentation.common.TestListItem

@Composable
fun ArticlesScreen() {
    Column {
        SearchBar()
        LazyColumn {
            items(articles) { article ->
//                ItemCard(
//                    name = article.title,
//                    leadingIcon = article.leadingIcon,
//                )
                TestListItem(
                    title = article.title,
                    leadingIcon = article.leadingIcon
                )
            }
        }
    }
}