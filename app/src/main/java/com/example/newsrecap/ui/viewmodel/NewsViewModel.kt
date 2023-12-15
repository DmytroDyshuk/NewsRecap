package com.example.newsrecap.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newsrecap.data.local.getDatabase
import com.example.newsrecap.data.network.RetrofitService
import com.example.newsrecap.domain.model.News
import com.example.newsrecap.data.repository.NewsRepository
import com.example.newsrecap.ui.ui_states.NewsUiState
import com.example.newsrecap.utils.constants.SourcesConstants
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

    private var currentSource: String = SourcesConstants.ALL_NEWS

    init {
        getNewsListBySource()
    }

    fun getNewsListBySource() {
        viewModelScope.launch {
            try {
                loadingStarted()
                val newsList: List<News> = if (currentSource == SourcesConstants.ALL_NEWS) {
                    newsRepository.refreshNews()
                } else newsRepository.getNewsBySource(currentSource)
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

    fun setCurrentSource(source: String) {
        currentSource = source
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