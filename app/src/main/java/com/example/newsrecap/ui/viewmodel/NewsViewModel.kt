package com.example.newsrecap.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsrecap.model.News
import com.example.newsrecap.network.NewsApi
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {

    private val _newsList = MutableLiveData<List<News>>()
    private val _source = MutableLiveData<String>()

    val newsList: LiveData<List<News>> = _newsList
    val source: LiveData<String> = _source

    fun getNewsList() {
        viewModelScope.launch {
            try {
                source.value?.let {
                    val newsResult = NewsApi.retrofitService.getNews(it)
                    _newsList.value = newsResult.articles
                }
            } catch (e: Exception) {
                _newsList.value = listOf()
            }
        }
    }

    fun setNewSource(source: String) {
        _source.value = source
    }
}