package com.wcsm.confectionaryadmin.data.model.states

data class OrderSyncState(
    val isSincronized: Boolean = false,
    val syncError: Boolean = false
)
