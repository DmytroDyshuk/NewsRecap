package com.example.newsrecap.utils.constants

import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer.ValueParametersHandler.DEFAULT

object Constants {
    const val BASE_URL = "https://newsapi.org/v2/"

    const val DOMAINS = "wsj.com, washingtonpost.com, time.com, ign.com"

    const val DEFAULT_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val RESERVE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'"
}