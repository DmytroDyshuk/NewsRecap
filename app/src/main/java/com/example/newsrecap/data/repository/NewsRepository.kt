package com.example.newsrecap.data.repository

import com.example.newsrecap.data.local.NewsDatabase
import com.example.newsrecap.data.local.entities.asDomainModel
import com.example.newsrecap.data.network.NewsApiService
import com.example.newsrecap.domain.model.News
import com.example.newsrecap.data.network.models.asEntity
import com.example.newsrecap.data.network.models.asDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class NewsRepository(
    private val newsDatabase: NewsDatabase,
    private val newsApi: NewsApiService,
    private val ioDispatcher: CoroutineDispatcher
) {

    val news: Flow<List<News>> = newsDatabase.newsDao.getNews().map {
        it.asDomainModel()
    }

    suspend fun refreshNews(): List<News> = withContext(ioDispatcher) {
        val news = newsApi.getEverythingNews()
        newsDatabase.newsDao.insertAllNews(news.articles.map { it.asEntity() })
        return@withContext news.articles.map { it.asDomainModel() }
    }

    suspend fun getNewsBySource(source: String): List<News> = withContext(ioDispatcher) {
        return@withContext newsApi.getNews(source)
            .asDomainModel().articles
    }

}