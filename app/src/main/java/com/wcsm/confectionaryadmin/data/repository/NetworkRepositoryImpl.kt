package com.wcsm.confectionaryadmin.data.repository

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkRepositoryImpl(
    private val connectivityManager: ConnectivityManager
) : NetworkRepository {
    override fun isConnected(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}