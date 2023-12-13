package com.example.newsrecap.data.repository

import com.example.newsrecap.utils.connectivity_observer.ConnectivityObserver
import com.example.newsrecap.utils.connectivity_observer.NetworkConnectivityObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class NetworkStatusRepository(
    private val coroutineScope: CoroutineScope,
    private val connectivityObserver: NetworkConnectivityObserver
) {
    val connectionStatus = connectivityObserver.observe()
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ConnectivityObserver.Status.Unavailable
        )
}