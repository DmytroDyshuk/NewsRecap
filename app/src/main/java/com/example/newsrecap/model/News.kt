package com.example.newsrecap.model

import java.util.IdentityHashMap

data class News(
    val id: Long = 0,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)