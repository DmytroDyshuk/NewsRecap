package com.example.newsrecap.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newsrecap.data.database.getDatabase
import com.example.newsrecap.data.network.RetrofitService
import com.example.newsrecap.domain.model.News
import com.example.newsrecap.data.repositories.NewsRepository
import com.example.newsrecap.ui.ui_states.NewsUiState
import com.example.newsrecap.utils.connectivity_observer.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val newsRepository = NewsRepository(
        newsDatabase = getDatabase(application),
        newsApi = RetrofitService.newsApiService,
        ioDispatcher = Dispatchers.IO
    )

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private val _selectedNews = MutableLiveData<News>()
    val selectedNews: LiveData<News> = _selectedNews

    init {
        if (NetworkUtil.isInternetAvailable(application.applicationContext)) {
            refreshNews()
        } else {
            getLocalNews()
        }
    }

    fun refreshNews() {
        viewModelScope.launch {
            try {
                loadingStarted()
                val refreshedNewsList = newsRepository.refreshNews()
                _uiState.update {
                    it.copy(
                        newsList = refreshedNewsList,
                        isLoading = false
                    )
                }
            } catch (ioException: IOException) {
                _uiState.update {
                    it.copy(
                        errorMessage = ioException.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun getNewsListBySource(source: String) {
        viewModelScope.launch {
            try {
                loadingStarted()
                val newsList = newsRepository.getNewsBySource(source)
                _uiState.update {
                    it.copy(
                        newsList = newsList,
                        isLoading = false
                    )
                }
            } catch (ioException: IOException) {
                _uiState.update {
                    it.copy(
                        errorMessage = ioException.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun setNewSource(source: String) {
        _uiState.update {
            it.copy(source = source)
        }
    }

    fun setNewsTitle(title: String) {
        _uiState.update {
            it.copy(newsTitle = title)
        }
    }

    fun setSelectedNews(news: News) {
        _selectedNews.value = news
    }

    private fun loadingStarted() {
        _uiState.update {
            it.copy(isLoading = true)
        }
    }

    private fun getLocalNews() {
        viewModelScope.launch {
            try {
                newsRepository.news.collect { newsList ->
                    _uiState.update {
                        it.copy(newsList = newsList)
                    }
                }
            } catch (ioException: IOException) {
                _uiState.update {
                    it.copy(errorMessage = ioException.message)
                }
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel")
        }
    }

}