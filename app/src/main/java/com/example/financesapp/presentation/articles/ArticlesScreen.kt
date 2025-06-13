package com.example.financesapp.presentation.articles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import com.example.financesapp.data.mock.articles
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.presentation.common.SearchBar

@Composable
fun ArticlesScreen() {
    Column {
        SearchBar()
        LazyColumn {
            items(articles) { article ->
                ListItem(
                    title = article.title,
                    leadingIcon = article.leadingIcon,
                    onClick = { } //TODO()
                )
                HorizontalDivider()
            }
        }
    }
}