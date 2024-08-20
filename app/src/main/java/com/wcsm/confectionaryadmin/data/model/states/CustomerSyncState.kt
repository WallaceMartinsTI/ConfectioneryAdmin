package com.wcsm.confectionaryadmin.data.model.states

data class CustomerSyncState(
    val isSincronized: Boolean = false,
    val syncError: Boolean = false
)
