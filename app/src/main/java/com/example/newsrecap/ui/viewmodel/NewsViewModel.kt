package com.example.newsrecap.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsrecap.model.News
import com.example.newsrecap.network.NewsApi
import kotlinx.coroutines.launch

class NewsViewModel(): ViewModel() {

    private val _status = MutableLiveData<String>()
    private val _newsList = MutableLiveData<List<News>>()

    val status: LiveData<String> = _status
    val newsList:LiveData<List<News>> = _newsList

    init {
        getNewsList()
    }

    private fun getNewsList() {
        viewModelScope.launch {
            try {
                val newsResult = NewsApi.retrofitService.getNews()
                _newsList.value = newsResult.articles
                _status.value = "Success: ${newsResult.status} News retrieved"
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
                _newsList.value = listOf()
            }
        }
    }
}