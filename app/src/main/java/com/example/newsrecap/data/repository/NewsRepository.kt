package com.example.newsrecap.data.repository

import com.example.newsrecap.data.local.NewsDatabase
import com.example.newsrecap.data.local.entities.asDomainModel
import com.example.newsrecap.data.network.NewsApiService
import com.example.newsrecap.domain.model.News
import com.example.newsrecap.data.network.models.asEntity
import com.example.newsrecap.data.network.models.asDomainModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext

class NewsRepository(
    private val newsDatabase: NewsDatabase,
    private val newsApi: NewsApiService,
    private val ioDispatcher: CoroutineDispatcher,
    private val externalScope: CoroutineScope
) {

    val news = newsDatabase.newsDao.getNews().map {
        it.asDomainModel()
    }.stateIn(
        scope = externalScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )

    suspend fun refreshNews() {
        withContext(ioDispatcher) {
            val news = newsApi.getEverythingNews()
            newsDatabase.newsDao.deleteAllNews()
            newsDatabase.newsDao.insertAllNews(news.articles.map { it.asEntity() })
        }
    }

    suspend fun getNewsBySource(source: String): List<News> = withContext(ioDispatcher) {
        return@withContext newsApi.getNews(source).asDomainModel().articles
    }

}