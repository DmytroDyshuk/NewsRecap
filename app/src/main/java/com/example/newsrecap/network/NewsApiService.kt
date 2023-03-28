package com.example.newsrecap.network

import com.example.newsrecap.BuildConfig
import com.example.newsrecap.model.NewsResponse
import retrofit2.Retrofit
import retrofit2.http.GET
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/v2/"

private const val DOMAINS = "wsj.com, washingtonpost.com, time.com, ign.com, cnn.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getNews(@Query("sources") source: String, @Query("apiKey") apiKey: String = BuildConfig.MY_API_KEY): NewsResponse

    @GET("everything")
    suspend fun getEverythingNews(@Query("domains") domains: String = DOMAINS, @Query("apiKey") apiKey: String = BuildConfig.MY_API_KEY): NewsResponse
}

object NewsApi {
    val retrofitService : NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }
}