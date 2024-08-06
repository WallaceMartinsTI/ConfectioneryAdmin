package com.wcsm.confectionaryadmin.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrdersViewModel : ViewModel() {

    private val _filterResult = MutableStateFlow("")
    val filterResult = _filterResult.asStateFlow()

    fun updateFilterResult(newResult: String) {
        _filterResult.value = newResult
    }
}