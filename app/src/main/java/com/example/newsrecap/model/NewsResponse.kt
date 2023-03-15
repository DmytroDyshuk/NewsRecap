package com.example.newsrecap.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<News>
)