package com.example.financesapp.presentation.articles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import com.example.financesapp.data.mock.articles
import com.example.financesapp.presentation.common.ListItem
import com.example.financesapp.presentation.common.SearchBar
import com.example.financesapp.presentation.common.TopAppBarState
import com.example.financesapp.presentation.common.TopAppBarStateProvider

@Composable
fun ArticlesTestScreen() {
    TopAppBarStateProvider.update(TopAppBarState(title = "Мои статьи"))
    Column {
        SearchBar()
        HorizontalDivider()
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