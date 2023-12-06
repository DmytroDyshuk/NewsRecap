package com.example.newsrecap.ui.ui_states

import com.example.newsrecap.domain.model.News
import com.example.newsrecap.utils.connectivity_observer.ConnectivityObserver

data class NewsUiState(
    val newsList: List<News> = listOf(),
    val source: String = "",
    val newsTitle: String = "All News",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val networkStatus: ConnectivityObserver.Status = ConnectivityObserver.Status.Available
)
