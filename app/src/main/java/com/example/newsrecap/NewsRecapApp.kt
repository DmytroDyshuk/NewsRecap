package com.example.newsrecap

import android.app.Application
import com.example.newsrecap.utils.connectivity_observer.NetworkConnectivityObserver
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsRecapApp : Application() {
    val networkConnectivityObserver: NetworkConnectivityObserver by lazy {
        NetworkConnectivityObserver(applicationContext)
    }
}