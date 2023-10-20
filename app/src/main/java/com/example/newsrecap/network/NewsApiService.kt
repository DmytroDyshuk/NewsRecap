package com.example.newsrecap.network

import com.example.newsrecap.BuildConfig
import com.example.newsrecap.network.model.NewsResponseDto
import com.example.newsrecap.utils.constants.Constants.DOMAINS
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getNews(
        @Query("sources") source: String,
        @Query("apiKey") apiKey: String = BuildConfig.MY_API_KEY
    ): NewsResponseDto

    @GET("everything")
    suspend fun getEverythingNews(
        @Query("domains") domains: String = DOMAINS,
        @Query("apiKey") apiKey: String = BuildConfig.MY_API_KEY
    ): NewsResponseDto
}