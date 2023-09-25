package com.example.newsrecap.network.model

import com.example.newsrecap.database.model.DbNews
import com.example.newsrecap.domain.model.News
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsDto(
    val source: SourceDto?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

fun NewsDto.asDomainModel(): News {
    return News(
        source = this@asDomainModel.source?.asDomainModel(),
        author = this@asDomainModel.author,
        title = this@asDomainModel.title,
        description = this@asDomainModel.description,
        url = this@asDomainModel.url,
        urlToImage = this@asDomainModel.urlToImage,
        publishedAt = this@asDomainModel.publishedAt,
        content = this@asDomainModel.content
    )
}

fun NewsDto.asDatabaseModel(): DbNews {
    return DbNews(
        source = this@asDatabaseModel.source?.asDomainModel(),
        author = this@asDatabaseModel.author,
        title = this@asDatabaseModel.title,
        description = this@asDatabaseModel.description,
        url = this@asDatabaseModel.url,
        urlToImage = this@asDatabaseModel.urlToImage,
        publishedAt = this@asDatabaseModel.publishedAt,
        content = this@asDatabaseModel.content
    )
}