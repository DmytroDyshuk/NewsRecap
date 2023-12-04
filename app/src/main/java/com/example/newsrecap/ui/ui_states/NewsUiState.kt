package com.example.newsrecap.ui.ui_states

import com.example.newsrecap.domain.model.News

data class NewsUiState(
    val newsList: List<News> = listOf(),
    val source: String = "",
    val newsTitle: String = "All News",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
