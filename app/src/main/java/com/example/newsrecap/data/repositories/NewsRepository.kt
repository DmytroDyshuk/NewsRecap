package com.example.newsrecap.data.repositories

import com.example.newsrecap.data.database.NewsDatabase
import com.example.newsrecap.data.database.model.asDomainModel
import com.example.newsrecap.data.network.NewsApiService
import com.example.newsrecap.domain.model.News
import com.example.newsrecap.data.network.model.asDatabaseModel
import com.example.newsrecap.data.network.model.asDomainModel
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
        newsDatabase.newsDao.insertAllNews(news.articles.map { it.asDatabaseModel() })
        return@withContext news.articles.map { it.asDomainModel() }
    }

    suspend fun getNewsBySource(source: String): List<News> = withContext(ioDispatcher) {
        return@withContext newsApi.getNews(source)
            .asDomainModel().articles
    }

}