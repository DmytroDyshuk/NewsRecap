package com.example.newsrecap.data.repository

import com.example.newsrecap.data.local.dao.NewsDao
import com.example.newsrecap.data.local.entities.asDomainModel
import com.example.newsrecap.data.network.NewsApiService
import com.example.newsrecap.domain.model.News
import com.example.newsrecap.data.network.models.asEntity
import com.example.newsrecap.data.network.models.asDomainModel
import com.example.newsrecap.di.IoDispatcher
import com.example.newsrecap.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
    private val newsApi: NewsApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val externalScope: CoroutineScope
) : NewsRepository {

    override fun getNewsFlow(): StateFlow<List<News>> {
        return newsDao.getNews().map {
            it.asDomainModel()
        }.stateIn(
            scope = externalScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )
    }

    override suspend fun refreshNews() {
        withContext(ioDispatcher) {
            val news = newsApi.getEverythingNews()
            newsDao.deleteAllNews()
            newsDao.insertAllNews(news.articles.map { it.asEntity() })
        }
    }

    override suspend fun getNewsBySource(source: String): List<News> = withContext(ioDispatcher) {
        return@withContext newsApi.getNews(source).asDomainModel().articles
    }

}