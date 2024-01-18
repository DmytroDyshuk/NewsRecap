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
import com.example.newsrecap.utils.constants.SourcesConstants.ALL_NEWS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val newsRepository = NewsRepository(
        newsDatabase = getDatabase(application),
        newsApi = RetrofitService.newsApiService,
        ioDispatcher = Dispatchers.IO,
        externalScope = viewModelScope
    )

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private val _selectedNews = MutableLiveData<News>()
    val selectedNews: LiveData<News> = _selectedNews

    private var currentSource: String = ALL_NEWS

    init {
        viewModelScope.launch {
            newsRepository.news.collect { newsList ->
                _uiState.update {
                    it.copy(
                        newsList = newsList,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun getNews() {
        viewModelScope.launch {
            loadingStarted()
            try {
                if (currentSource == ALL_NEWS) {
                    newsRepository.refreshNews()
                } else {
                    val newsBySource = newsRepository.getNewsBySource(currentSource)
                    _uiState.update {
                        it.copy(
                            newsList = newsBySource,
                            isLoading = false
                        )
                    }
                }
            } catch (exception: Exception) {
                setErrorState(exception.message)
            }
            loadingEnded()
        }
    }

    fun setCurrentSource(source: String) {
        currentSource = source
    }

    fun setNewsTitleState(title: String) {
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

    private fun loadingEnded() {
        _uiState.update {
            it.copy(isLoading = false)
        }
    }

    private fun setErrorState(exceptionMessage: String?) {
        _uiState.update {
            it.copy(errorMessage = exceptionMessage)
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