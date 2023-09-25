package com.example.newsrecap.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.newsrecap.BuildConfig
import com.example.newsrecap.database.NewsDatabase
import com.example.newsrecap.database.model.asDomainModel
import com.example.newsrecap.domain.model.News
import com.example.newsrecap.network.RetrofitService
import com.example.newsrecap.network.model.asDatabaseModel
import com.example.newsrecap.network.model.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(private val newsDatabase: NewsDatabase) {

    val news: LiveData<List<News>> = newsDatabase.newsDao.getNews().map {
        it.asDomainModel()
    }

    suspend fun refreshNews(): List<News> = withContext(Dispatchers.IO) {
        val news = RetrofitService.newsApiService.getEverythingNews()
        newsDatabase.newsDao.insertAllNews(news.articles.map { it.asDatabaseModel() })
        return@withContext news.articles.map { it.asDomainModel() }
    }

    suspend fun getNewsBySource(source: String): List<News> = withContext(Dispatchers.IO) {
        return@withContext RetrofitService.newsApiService.getNews(source, BuildConfig.MY_API_KEY).asDomainModel().articles
    }

}