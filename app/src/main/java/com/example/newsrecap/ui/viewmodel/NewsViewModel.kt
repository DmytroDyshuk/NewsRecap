package com.example.newsrecap.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsrecap.domain.model.News
import com.example.newsrecap.ui.ui_states.NewsUiState
import com.example.newsrecap.domain.model.Sources
import com.example.newsrecap.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    private val _selectedNews = MutableLiveData<News>()
    val selectedNews: LiveData<News> = _selectedNews

    private val newsFlow = newsRepository.getNewsFlow()

    private var currentSource: Sources = Sources.ALL_NEWS

    init {
        viewModelScope.launch {
            newsFlow.collect { newsList ->
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
                if (currentSource == Sources.ALL_NEWS) {
                    newsRepository.refreshNews()
                } else {
                    val newsBySource = newsRepository.getNewsBySource(currentSource.source)
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

    fun setCurrentSource(source: Sources) {
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

}