package com.wcsm.confectionaryadmin.data.model.states

data class OrderSyncState(
    val isSynchronized: Boolean = false,
    val syncError: Boolean = false
)
