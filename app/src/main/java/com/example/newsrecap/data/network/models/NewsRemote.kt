package com.example.newsrecap.data.network.models

import com.example.newsrecap.data.local.entities.NewsEntity
import com.example.newsrecap.domain.model.News
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsRemote(
    val source: SourceDto?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

fun NewsRemote.asDomainModel(): News {
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

fun NewsRemote.asEntity(): NewsEntity {
    return NewsEntity(
        source = this@asEntity.source?.asDomainModel(),
        author = this@asEntity.author,
        title = this@asEntity.title,
        description = this@asEntity.description,
        url = this@asEntity.url,
        urlToImage = this@asEntity.urlToImage,
        publishedAt = this@asEntity.publishedAt,
        content = this@asEntity.content
    )
}