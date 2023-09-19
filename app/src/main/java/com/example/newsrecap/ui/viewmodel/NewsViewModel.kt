package com.example.newsrecap.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newsrecap.database.getDatabase
import com.example.newsrecap.repository.NewsRepository
import kotlinx.coroutines.launch
import java.io.IOException

class NewsViewModel(application: Application): AndroidViewModel(application) {

    private val newsRepository = NewsRepository(getDatabase(application))

    val newsList = newsRepository.news

    private val _source = MutableLiveData<String>()
    val source: LiveData<String> = _source

    init {
        refreshNewsFromRepository()
    }

    private fun refreshNewsFromRepository() {
        viewModelScope.launch {
            try {
                newsRepository.refreshNews()
            } catch (networkError: IOException) {
                //TODO: add error if newsList is empty
            }
        }
    }

    fun getNewsList() {
        viewModelScope.launch {
            try {
                source.value?.let {
                    refreshNewsFromRepository()
                }
            } catch (e: Exception) {
                Log.e("getNewsListError","e: ${e.message}")
            }
        }
    }

    fun setNewSource(source: String) {
        _source.value = source
    }

    class Factory(val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}