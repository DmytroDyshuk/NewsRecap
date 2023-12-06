package com.example.newsrecap

import android.app.Application
import com.example.newsrecap.utils.connectivity_observer.NetworkConnectivityObserver

class NewsRecapApp : Application() {
    val networkConnectivityObserver: NetworkConnectivityObserver by lazy {
        NetworkConnectivityObserver(applicationContext)
    }
}