package com.example.newsrecap.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newsrecap.R
import com.example.newsrecap.database.getDatabase
import com.example.newsrecap.domain.model.News
import com.example.newsrecap.repository.NewsRepository
import com.example.newsrecap.utils.network.NetworkUtil
import kotlinx.coroutines.launch
import java.io.IOException

class NewsViewModel(application: Application): AndroidViewModel(application) {

    private val newsRepository = NewsRepository(getDatabase(application))

    private val _newsList = MutableLiveData<List<News>>()
    val newsList: LiveData<List<News>> = _newsList

    private val _source = MutableLiveData<String>()
    val source: LiveData<String> = _source

    private val _selectedNews = MutableLiveData<News>()
    val selectedNews: LiveData<News> = _selectedNews

    private val _newsTitle = MutableLiveData<String>().apply {
        value = application.getString(R.string.all_news)
    }
    val newsTitle: LiveData<String> = _newsTitle

    init {
        if (NetworkUtil.isInternetAvailable(application.applicationContext)) {
            refreshNews()
        } else {
            newsRepository.news.observeForever {
                _newsList.value = it
            }
        }
    }

    fun refreshNews() {
        viewModelScope.launch {
            try {
                _newsList.value = newsRepository.refreshNews()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun getNewsList() {
        viewModelScope.launch {
            try {
                source.value?.let {
                    _newsList.value = newsRepository.getNewsBySource(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun setNewSource(source: String) {
        _source.value = source
    }

    fun setSelectedNews(news: News) {
        _selectedNews.value = news
    }

    fun setNewsTitle(title: String) {
        _newsTitle.value = title
    }

    class Factory(val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }

}