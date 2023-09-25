package com.example.newsrecap.network.model

import com.example.newsrecap.domain.model.NewsResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponseDto(
    @Json(name = "status") val status: String,
    @Json(name = "totalResults") val totalResults: Int,
    @Json(name = "articles") val articles: List<NewsDto>
)

fun NewsResponseDto.asDomainModel(): NewsResponse {
    return NewsResponse(
        status = status,
        totalResults = totalResults,
        articles = articles.map { it.asDomainModel()  }
    )
}