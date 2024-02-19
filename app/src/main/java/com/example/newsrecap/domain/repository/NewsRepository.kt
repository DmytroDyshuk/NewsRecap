package com.example.newsrecap.domain.repository

import com.example.newsrecap.domain.model.News
import kotlinx.coroutines.flow.StateFlow

interface NewsRepository {
    fun getNewsFlow(): StateFlow<List<News>>
    suspend fun refreshNews()
    suspend fun getNewsBySource(source: String): List<News>
}