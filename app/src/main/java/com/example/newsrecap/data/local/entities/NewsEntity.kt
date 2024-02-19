package com.example.newsrecap.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsrecap.domain.model.News
import com.example.newsrecap.domain.model.Source

@Entity
data class NewsEntity constructor(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    @PrimaryKey(autoGenerate = false)
    val publishedAt: String = "",
    val content: String?
)

fun List<NewsEntity>.asDomainModel(): List<News> {
    return map {
        News(
            source = it.source,
            author = it.author,
            title = it.title,
            description = it.description,
            url = it.url,
            urlToImage = it.urlToImage,
            publishedAt = it.publishedAt,
            content = it.content
        )
    }
}