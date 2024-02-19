package com.example.newsrecap.data.repository

import com.example.newsrecap.utils.connectivity_observer.ConnectivityObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class NetworkStatusRepository(
    private val externalScope: CoroutineScope,
    private val connectivityObserver: ConnectivityObserver
) {
    val connectionStatus = connectivityObserver.observe()
        .stateIn(
            scope = externalScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ConnectivityObserver.Status.Unavailable
        )
}