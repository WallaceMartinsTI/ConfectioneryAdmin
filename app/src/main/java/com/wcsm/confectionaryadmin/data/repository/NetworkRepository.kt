package com.wcsm.confectionaryadmin.data.repository

interface NetworkRepository {
    fun isConnected(): Boolean
}