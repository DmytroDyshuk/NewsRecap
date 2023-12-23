package com.example.newsrecap.data.network.models

import com.example.newsrecap.domain.model.NewsResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponseRemote(
    @Json(name = "status") val status: String,
    @Json(name = "totalResults") val totalResults: Int,
    @Json(name = "articles") val articles: List<NewsRemote>
)

fun NewsResponseRemote.asDomainModel(): NewsResponse {
    return NewsResponse(
        status = status,
        totalResults = totalResults,
        articles = articles.map { it.asDomainModel() }
    )
}